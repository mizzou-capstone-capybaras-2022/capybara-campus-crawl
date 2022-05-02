package com.capybara.CapybaraCampusCrawlBackend.Routing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capybara.CapybaraCampusCrawlBackend.CapybaraCampusCrawlBackendApplication;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.GraphEdgeRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.GraphNodeRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.OpenRouteServiceDao;
import com.capybara.CapybaraCampusCrawlBackend.Models.CapybaraGraphEdgeForRouting;
import com.capybara.CapybaraCampusCrawlBackend.Models.GraphEdge;
import com.capybara.CapybaraCampusCrawlBackend.Models.GraphNode;
import com.capybara.CapybaraCampusCrawlBackend.Models.Point;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class RoutingSystem {

	private static final Logger logger = LoggerFactory.getLogger(CapybaraCampusCrawlBackendApplication.class);
	
	private List<CapybaraRouteNode> nodeList;
	
	@Inject
	public RoutingSystem(GraphEdgeRepository edgeDao, GraphNodeRepository nodeDao) {
		List<GraphNode> nodes = nodeDao.findAll();
		List<GraphEdge> edges = edgeDao.findAll();
		
		logger.info("Fetch nodes and edges");
	
		try {
			nodeList = generateNodes(nodes, edges);
		} catch (JsonProcessingException e) {
			nodeList = new ArrayList<>();
			e.printStackTrace();
		}
	}
	public List<Point> ComputeRoute(Long startingBuildingId, Long endingBuildingId) {
		List<Point> routePoints = GetRouteBetweenPoints(startingBuildingId-1, endingBuildingId-1, nodeList);		
		return routePoints;
	}
	private static List<CapybaraRouteNode> generateNodes(List<GraphNode> graphNodeList, List<GraphEdge> graphEdgeList) throws JsonProcessingException{
		List<CapybaraRouteNode> capybaraNodeList = constructNodes(graphNodeList);
		capybaraNodeList = addCapybaraEdges(capybaraNodeList, graphEdgeList);
	    return capybaraNodeList; 
	}
	private static List<CapybaraRouteNode> constructNodes(List<GraphNode> graphNodeList){
		List<CapybaraRouteNode> capybaraNodeList = new ArrayList<CapybaraRouteNode>();
		
		//generate nodes
		for (GraphNode currentNode : graphNodeList) {
			int nodeID = (int) (currentNode.getNodeID() - 1);
	    	String nodeDesc = currentNode.getDescription();
	    	double lat = currentNode.getLatitude();
	    	double lon = currentNode.getLongitude();
	    	
	    	CapybaraRouteNode capybaraNode = new CapybaraRouteNode(nodeID,nodeDesc,lat,lon);
	    	capybaraNodeList.add(capybaraNode);
		}
		
		return capybaraNodeList;
	}
	private static List<CapybaraRouteNode> addCapybaraEdges(List<CapybaraRouteNode> capybaraNodeList, List<GraphEdge> graphEdgeList) throws JsonProcessingException{
		//generate adj lists for every node
	    for (GraphEdge currentEdge: graphEdgeList) {
	    	int fromNode = Math.toIntExact(currentEdge.getFromNode().getNodeID());
		    int toNode = Math.toIntExact(currentEdge.getToNode().getNodeID());
		    
		    //get distance and modify for outside
		    double distance = getModifiedGraphEdgeWeight(currentEdge);

			List<Point> points = parseJSONPoints(currentEdge.getPathshape());

			List<Point> reversedPoints = new ArrayList<>(points);
			Collections.reverse(reversedPoints);

			capybaraNodeList.get(fromNode-1).addEdge(capybaraNodeList.get(toNode-1), distance,points);
			capybaraNodeList.get(toNode-1).addEdge(capybaraNodeList.get(fromNode-1), distance,reversedPoints);
	    }
	    
	    return capybaraNodeList;
	}

	private static double getModifiedGraphEdgeWeight(GraphEdge currentEdge){
		if(currentEdge.getFromToAction().equals("outsideWalking")) {
			return currentEdge.getDistance() * 6;
		}else {
			return currentEdge.getDistance();
		}
	}
	private static List<Point> parseJSONPoints(String pointsJson) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		JsonNode coordinates = mapper.readTree(pointsJson);

		ArrayList<Point> points = new ArrayList<Point>();

		for (JsonNode coordinateNode : coordinates) {
			Double latitude = coordinateNode.get(1).asDouble();
			Double longitude = coordinateNode.get(0).asDouble();

			points.add(new Point()
					.latitude(latitude)
					.longitude(longitude));
		}

		return points;
	}
	private static ArrayList<Point> ListPath(long fromNodeId, long toNodeID, List<CapybaraRouteNode> nodeList) {
		System.out.println("From Node "+fromNodeId +" To Node "+ toNodeID);
		
		CapybaraRouteNode currentNode = nodeList.get((int) toNodeID);
		List<CapybaraRouteNode> path = currentNode.getShortestPath();
		
		CapybaraRouteNode PreviousNode = null;
		ArrayList<Point> listToSend = new ArrayList<>();

		for(CapybaraRouteNode current : path) {
			if(PreviousNode != null) {
				List<Point> points = PreviousNode.getAdjacentNodes().entrySet().stream()
						.filter(adjacencyPair -> adjacencyPair.getKey().getID() == current.getID())
						.findFirst().get().getValue().coords;
				listToSend.addAll(points);
			}

			PreviousNode = current;
		}

		List<Point> points = PreviousNode.getAdjacentNodes().entrySet().stream()
				.filter(adjacencyPair -> adjacencyPair.getKey().getID() == currentNode.getID())
				.findFirst().get().getValue().coords;
		
		listToSend.addAll(points);

		return listToSend;
	}
	public static List<Point> GetRouteBetweenPoints(long start, long end, List<CapybaraRouteNode> nodeList){
		CapybaraRouteGraph graph = new CapybaraRouteGraph();
	    
	    for(int i=0;i<nodeList.size();i++) {
	    	graph.addNode(nodeList.get(i));
	    }
	    
		System.out.println("Starting routing shortest from NodeID " + start);
	    
		int found =-1;
	    int found2 =-1;
	    
	    ArrayList<Point> toReturn = new ArrayList<Point>();
	    
	    for(int i=0;i<nodeList.size();i++) {
	    	CapybaraRouteNode currentNode = nodeList.get(i);
	    	int currentID = currentNode.getID();
	    	if(start == currentID) {
	    		found = currentID;
	    	}
	    }
	    
	    if(found >= 0) {
	    	CapybaraRouteNode startNode = nodeList.get(found);
	    	CapybaraRouteGraph.calculateShortestPathFromSource(graph,startNode);
		    	    	
		    for(int i=0;i<nodeList.size();i++) {
		    	CapybaraRouteNode currentNode = nodeList.get(i);
		    	int currentID = currentNode.getID();
		    	if(end == currentID) {
		    		found2 = currentID;
		    	}
		    }
		    
		    toReturn = ListPath(found,found2,nodeList);
	    	
		    System.out.println(toReturn.size());
	    	
	    }else {
	    	
	    	System.out.println("COULDN'T FIND ID");
	    
	    }
	    return toReturn;
	}
	
}













