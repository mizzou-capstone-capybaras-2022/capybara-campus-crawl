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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
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
			// TODO Auto-generated catch block
			nodeList = new ArrayList<CapybaraRouteNode>();
			e.printStackTrace();
		}
	}
	
	public List<Point> ComputeRoute(Long startingBuildingId, Long endingBuildingId) throws JsonMappingException, JsonProcessingException {
		List<Point> routePoints = GetRouteBetweenPoints(startingBuildingId-1, endingBuildingId-1, nodeList);		
		return routePoints;
	}
		
	
	private static List<CapybaraRouteNode> generateNodes(List<GraphNode> graphNodeList, List<GraphEdge> graphEdgeList) throws JsonMappingException, JsonProcessingException{
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
	
	private static List<CapybaraRouteNode> addCapybaraEdges(List<CapybaraRouteNode> capybaraNodeList, List<GraphEdge> graphEdgeList) throws JsonMappingException, JsonProcessingException{
		//generate adj lists for every node
	    for (GraphEdge currentEdge: graphEdgeList) {
	    	int fromNode = (int)(currentEdge.getFromNode().getNodeID() - 0);
		    int toNode = (int)(currentEdge.getToNode().getNodeID() - 0);
		    
		    //get distance and modify for outside
		    double distance;
		    if(currentEdge.getFromToAction().equals("outsideWalking")) {
		    	distance = currentEdge.getDistance() * 6;
		    }else {
		    	distance = currentEdge.getDistance();
		    }

		    String coordsListJson = currentEdge.getPathshape();
		    ObjectMapper mapper = new ObjectMapper();
			JsonNode coordinates = mapper.readTree(coordsListJson);	
			
			ArrayList<Point> points = new ArrayList<Point>();
			
			for (JsonNode coordinateNode : coordinates) {
				Double latitude = coordinateNode.get(1).asDouble();
				Double longitude = coordinateNode.get(0).asDouble();
				
				points.add(new Point()
						.latitude(latitude)
						.longitude(longitude));
			}
			
			capybaraNodeList.get(fromNode-1).addEdge(capybaraNodeList.get(toNode-1), distance,points);
			 
			for (int z = 0, j = points.size() - 1; z < j; z++) {
		            points.add(z, points.remove(j));
	        }
			
		    capybaraNodeList.get(toNode-1).addEdge(capybaraNodeList.get(fromNode-1), distance,points);
	    }
	    
	    return capybaraNodeList;
	}

	
	private static ArrayList<Point> ListPath(long fromNodeId, long toNodeID, List<CapybaraRouteNode> nodeList) {
		System.out.println("From Node "+fromNodeId +" To Node "+ toNodeID);
		
		CapybaraRouteNode currentNode = nodeList.get((int) toNodeID);
		List<CapybaraRouteNode> path = currentNode.getShortestPath();
		
		CapybaraRouteNode PreviousNode = null;
		Double distance = (double)-1;
		
		ArrayList<Point> listToSend = new ArrayList<Point>();
		ArrayList<double[]> list = new ArrayList<double[]>();
		ArrayList<Point> points = new ArrayList<Point>();
		
		for(CapybaraRouteNode current : path) {
			if(PreviousNode != null) {
				for (Entry<CapybaraRouteNode, Pair> adjacencyPair : PreviousNode.getAdjacentNodes().entrySet()) {
			        if(current.getID() == adjacencyPair.getKey().getID()) {
			        	distance = adjacencyPair.getValue().distance;
			        	points = new ArrayList<Point>(adjacencyPair.getValue().coords);
			        }	          
			    }
				
				if(PreviousNode.getID() < current.getID()) {
					Collections.reverse(points);
				}
				
				listToSend.addAll(points);
				PreviousNode = current;
			}else {
				PreviousNode = current;
			}
		}
		
		for (Entry<CapybaraRouteNode, Pair> adjacencyPair : PreviousNode.getAdjacentNodes().entrySet()) {
	        if(currentNode.getID() == adjacencyPair.getKey().getID()) {
	        	distance = adjacencyPair.getValue().distance;
	        	points = new ArrayList<Point>(adjacencyPair.getValue().coords);
	        }	          
	    }
		
		if(PreviousNode.getID() < currentNode.getID()) {
			Collections.reverse(points);
		}
		
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













