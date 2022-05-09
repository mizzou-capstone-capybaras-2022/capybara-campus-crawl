package com.capybara.CapybaraCampusCrawlBackend.BusinessLogic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capybara.CapybaraCampusCrawlBackend.Controllers.BuildingsApiController;
import com.capybara.CapybaraCampusCrawlBackend.Models.Building;
import com.capybara.CapybaraCampusCrawlBackend.Models.BuildingRouteRequest;
import com.capybara.CapybaraCampusCrawlBackend.Models.GraphNode;
import com.capybara.CapybaraCampusCrawlBackend.Models.Location;
import com.capybara.CapybaraCampusCrawlBackend.Models.PitstopConstraint;
import com.capybara.CapybaraCampusCrawlBackend.Models.Point;
import com.capybara.CapybaraCampusCrawlBackend.Models.RouteRequest;
import com.capybara.CapybaraCampusCrawlBackend.Models.RouteRequestConstraints;
import com.capybara.CapybaraCampusCrawlBackend.Routing.RoutingSystem;

@Component
public class RoutingBll {

	private RoutingSystem routingDao;
	
	private BuildingBll buildingBll;
	
	@Autowired
	public RoutingBll(RoutingSystem routingDao, BuildingBll buildingBll) {
		this.routingDao = routingDao;
		this.buildingBll = buildingBll;
	}
	
	private static boolean hasNoConstraints(RouteRequestConstraints constraints) {
    	return constraints.getPreferIndoors() == false 
                && constraints.getStopForFood() == false 
                && constraints.getAvoidCrowds() == false
                && constraints.getPitstops().size() == 0
                && !constraints.getTimeConstraint().isPresent();
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
	
	public List<Point> fetchRoute(RouteRequest routeRequest){
		RouteRequestConstraints constraints = routeRequest.getConstraints();
		LogConstraints(constraints);

		List<PitstopConstraint> pitstopConstraints = constraints.getPitstops();
		List<Location> pitstopConstraintLocations = new ArrayList<Location>();

		for (PitstopConstraint pitstopConstraint : pitstopConstraints) {
			pitstopConstraintLocations.add(pitstopConstraint.getLocation());
			System.out.println(pitstopConstraint.getLocation());
		}
		
		List<Point> points = new ArrayList<Point>();
		
		BigDecimal buildingIdFrom = routeRequest.getFromLocation().getBuildingId();
    	BigDecimal buildingIdTo = routeRequest.getToLocation().getBuildingId();
    	
    	GraphNode graphNodeA = buildingBll.fetchBuildingGraphNode(buildingIdFrom);
		GraphNode graphNodeB = buildingBll.fetchBuildingGraphNode(buildingIdTo);;
		
		try {
			boolean preferIndoors = constraints.getPreferIndoors();
			points = routingDao.ComputeRoute(graphNodeA.getNodeID(), graphNodeB.getNodeID(), preferIndoors);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return points;
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
