package com.capybara.CapybaraCampusCrawlBackend.BusinessLogic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.capybara.CapybaraCampusCrawlBackend.DataAccess.PlaceRepository;
import com.capybara.CapybaraCampusCrawlBackend.Models.*;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jgrapht.Graph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capybara.CapybaraCampusCrawlBackend.Controllers.BuildingsApiController;
import com.capybara.CapybaraCampusCrawlBackend.Routing.RoutingSystem;

@Component
public class RoutingBll {

	private RoutingSystem routingDao;

	private PlaceRepository placesDao;

	private BuildingBll buildingBll;
	
	@Autowired
	public RoutingBll(RoutingSystem routingDao, BuildingBll buildingBll, PlaceRepository placesDao) {
		this.routingDao = routingDao;
		this.placesDao = placesDao;
		this.buildingBll = buildingBll;
	}

    private static void LogConstraints(RouteRequestConstraints constraints) {
    	 System.out.println("Prefer Indoors: " + constraints.getPreferIndoors());
         System.out.println("Stop by food: " + constraints.getStopForFood());
         System.out.println("Avoid Crouds:" + constraints.getAvoidCrowds());
         
         List<PitstopConstraint> pitstops = constraints.getPitstops();
         
         if (pitstops.size() > 0) {
             Location location = pitstops.get(0).getLocation();
             
             System.out.println("Building ID: " + location.getBuildingId());
             System.out.println("Building Lat: " + location.getLatitude());
             System.out.println("Building Long: " + location.getLongitude());
         }
         
         if (constraints.getTimeConstraint().isPresent()) {
             System.out.println("Max Time: "+ constraints.getTimeConstraint().get().getMaxTime());
         }else {
         	System.out.println("Max Time: "+ "null");
         }
    }

	private List<GraphNode> deconstructRoutePairs(List<Pair<GraphNode, GraphNode>> routePairs){
		List<GraphNode> nodes = new ArrayList<>();

		for (Pair<GraphNode, GraphNode> pair : routePairs){
			if (routePairs.indexOf(pair) == routePairs.size() - 1){
				nodes.add(pair.getLeft());
				nodes.add(pair.getRight());
			}else{
				nodes.add(pair.getLeft());
			}
		}

		return nodes;
	}

	private List<Pair<GraphNode, GraphNode>> constructRoutePairs(List<GraphNode> nodes){
		List<Pair<GraphNode, GraphNode>> routingPairs = new ArrayList<>();

		int previousNodeIdx = 0;
		for (int i = 1; i < nodes.size(); i++){
			GraphNode previousNode = nodes.get(previousNodeIdx);
			GraphNode currentNode = nodes.get(i);

			MutablePair<GraphNode, GraphNode> routingPair = new MutablePair<>();
			routingPair.setLeft(previousNode);
			routingPair.setRight(currentNode);
			routingPairs.add(routingPair);

			previousNodeIdx = i;
		}

		return routingPairs;
	}

	private List<Pair<GraphNode, GraphNode>> constructRoutePairs(GraphNode startingNode, List<PitstopConstraint> pitstops, GraphNode endingNode){
		List<GraphNode> pitstopConstraintGraphNodes = pitstops.stream()
				.map(pitstopConstraint -> pitstopConstraint.getLocation())
				.map(location -> location.getBuildingId())
				.map(buildingId -> buildingBll.fetchBuildingGraphNode(buildingId))
				.collect(Collectors.toList());

		pitstopConstraintGraphNodes.add(0, startingNode);
		pitstopConstraintGraphNodes.add(endingNode);

		return constructRoutePairs(pitstopConstraintGraphNodes);
	}

	private List<Point> determineConcatenatedRoute(List<Pair<GraphNode, GraphNode>> routePairs, boolean preferIndoors){
		List<Point> points = new ArrayList<Point>();

		for (Pair<GraphNode, GraphNode> routingPair : routePairs){
			List<Point> tempPoints = new ArrayList<>();

			try {
				tempPoints = routingDao.ComputeRoute(routingPair.getLeft().getNodeID(), routingPair.getRight().getNodeID(), preferIndoors);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			points.addAll(tempPoints);
		}

		return points;
	}

	private Double determineConcatenatedRouteDistance(List<Pair<GraphNode, GraphNode>> routePairs, boolean preferIndoors){
		double totalWeight = 0;

		for (Pair<GraphNode, GraphNode> routingPair : routePairs){
			double routeWeight = 0;

			try {
				routeWeight = routingDao.ComputeRouteDistance(routingPair.getLeft().getNodeID(), routingPair.getRight().getNodeID(), preferIndoors);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			totalWeight += routeWeight;
		}

		return totalWeight;
	}

	private List<Place> fetchFoodPlaces(){
		List<Place> foodPlaces = this.placesDao.findAll().stream().filter(place -> place.getPlaceType() == PlaceType.DINING)
				.collect(Collectors.toList());

		return foodPlaces;
	}

	private List<Pair<GraphNode, GraphNode>> determineOptimalFoodRoute(List<Pair<GraphNode, GraphNode>> routingPairs, boolean preferIndoors){
		List<Place> foodPlaces = fetchFoodPlaces();
		double minRouteWeight = Double.POSITIVE_INFINITY;
		List<Pair<GraphNode, GraphNode>> chosenFoodRoute = null;

		//If we want to stop by food, we need to do two of the following
		//We need to find the location of food that

		//minimizes the route distance compared to other food
		for (Place place : foodPlaces){
			List<GraphNode> flattenedNodes = deconstructRoutePairs(routingPairs);

			flattenedNodes.add(1, place.getNode());

			List<Pair<GraphNode, GraphNode>> canidateFoodRoute = constructRoutePairs(flattenedNodes);

			double routeWeight = determineConcatenatedRouteDistance(canidateFoodRoute, preferIndoors);

			if (routeWeight <= minRouteWeight){
				minRouteWeight = routeWeight;
				chosenFoodRoute = canidateFoodRoute;
			}

		}

		return chosenFoodRoute;
	}

	public List<Point> fetchRoute(RouteRequest routeRequest){
		RouteRequestConstraints constraints = routeRequest.getConstraints();
		boolean preferIndoors = constraints.getPreferIndoors();
		boolean stopByFood = constraints.getStopForFood();

		LogConstraints(constraints);

    	GraphNode startingGraphNode = buildingBll.fetchBuildingGraphNode(routeRequest.getFromLocation().getBuildingId());
		GraphNode endingGraphNode = buildingBll.fetchBuildingGraphNode(routeRequest.getToLocation().getBuildingId());;

		List<Pair<GraphNode, GraphNode>> routingPairs = constructRoutePairs(startingGraphNode, constraints.getPitstops(), endingGraphNode);
		List<Pair<GraphNode, GraphNode>> chosenRoutePairSet = routingPairs;

		if (stopByFood){
			List<Pair<GraphNode, GraphNode>> chosenFoodRoute = determineOptimalFoodRoute(routingPairs, preferIndoors);
			if (chosenFoodRoute != null){
				chosenRoutePairSet = chosenFoodRoute;
			}
		}

		return determineConcatenatedRoute(chosenRoutePairSet, preferIndoors);
	}
	
	public List<Point> fetchRoute(BuildingRouteRequest buildingRouteRequest){
		RouteRequest routeRequest = new RouteRequest();
		
		Location fromLocation = new Location();
		fromLocation.setBuildingId(buildingRouteRequest.getFromBuilding().getBuildingId());
		
		Location toLocation = new Location();
		toLocation.setBuildingId(buildingRouteRequest.getToBuilding().getBuildingId());
		
		
		
		RouteRequestConstraints nullConstraints = new RouteRequestConstraints();
		nullConstraints.setAvoidCrowds(false);
		nullConstraints.setPreferIndoors(false);
		nullConstraints.setStopForFood(false);
		nullConstraints.setPitstops(new ArrayList<>());
		nullConstraints.setTimeConstraint(Optional.empty());
		
		routeRequest.setFromLocation(fromLocation);
		routeRequest.setToLocation(toLocation);
		routeRequest.setConstraints(nullConstraints);
		return fetchRoute(routeRequest);
	}
}
