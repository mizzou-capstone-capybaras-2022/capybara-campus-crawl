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
	
	
	private GraphEdgeRepository edgeDao;
	
	private GraphNodeRepository nodeDao;
	
	private Graph<Long,CapybaraGraphEdgeForRouting> mizzouGraph;
	
	private HashMap<Long, GraphNode> graphNodeLookupTable = new HashMap<>();
	
	List<GraphNode> nodes;
	List<GraphEdge> edges;
	
	
	@Inject
	public RoutingSystem(GraphEdgeRepository edgeDao, GraphNodeRepository nodeDao) {
		this.edgeDao = edgeDao;
		this.nodeDao = nodeDao;
		
		mizzouGraph = new DefaultUndirectedWeightedGraph<Long, CapybaraGraphEdgeForRouting>(CapybaraGraphEdgeForRouting.class);
		
		nodes = nodeDao.findAll();
		edges = edgeDao.findAll();
		nodes.get(0).getNodeID();
		for (GraphNode node : nodes) {
			
			graphNodeLookupTable.put(node.getNodeID(), node);
			mizzouGraph.addVertex(node.getNodeID());
		}
		
		
		
		for (GraphEdge edge: edges) {
			CapybaraGraphEdgeForRouting edgeForRouting = new CapybaraGraphEdgeForRouting(edge.getEdgeId());
			mizzouGraph.addEdge(edge.getFromNode().getNodeID(), edge.getToNode().getNodeID(), edgeForRouting);
			mizzouGraph.setEdgeWeight(edgeForRouting, edge.getDistance());
		}
	}
	
	public List<Point> ComputeRoute(Long startingBuildingId, Long endingBuildingId) throws JsonMappingException, JsonProcessingException {
		System.out.println("PRESSED BUTTON1");
		List<Node> nodeList = generateNodes(nodes,edges);
		System.out.println("PRESSED BUTTON2");
		//printADJ(nodeList);
		List<Point> pointsList2 = GetRouteBetweenPoints(startingBuildingId-1,endingBuildingId-1,nodeList);
		System.out.println(Arrays.toString(pointsList2.toArray()));
		
		return pointsList2;
		
		
		
	}
	
	private static void printADJ(List<Node> nodeList) {
	    for(int i=0;i<nodeList.size();i++) {
	    	Node currentNode = nodeList.get(i);
	    	System.out.println("Adj List for Node of ID:"+currentNode.getID()+"    Desc:"+currentNode.getDescription());
	    	for (Entry<Node, Pair> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
		        Node adjacentNode = adjacencyPair.getKey();
		        Double edgeWeigh = adjacencyPair.getValue().distance;
		        System.out.println("        ID:"+adjacentNode.getID()+" Desc:"+adjacentNode.getDescription()+ "  Weight:"+edgeWeigh);
		    }
	    }
	}
	
	private static List<Node> generateNodes(List<GraphNode> nodeObj, List<GraphEdge> edgeObj) throws JsonMappingException, JsonProcessingException{
		List<Node> nodeList = new ArrayList<Node>();
		//generate nodes
	    for(int i =0;i<nodeObj.size();i++) {
	    	GraphNode currentJSON = nodeObj.get(i);
	    	
	    	int nodeID = (int) (currentJSON.getNodeID()-1);
	    	String nodeDesc = currentJSON.getDescription();
	    	double lat = currentJSON.getLatitude();
	    	double lon = currentJSON.getLongitude();
	    	
	    	Node node = new Node(nodeID,nodeDesc,lat,lon);
	    	nodeList.add(node);
	    }
	    
	    System.out.println("Starting inside routing");
	    //generate adj lists for every node
	    for(int i=0;i<edgeObj.size();i++) {
		    int fromNode = (int)(edgeObj.get(i).getFromNode().getNodeID() -0);
		    int toNode = (int)(edgeObj.get(i).getToNode().getNodeID() -0);
		    
		    //get distance and modify for outside
		    double distance;
		    if(edgeObj.get(i).getFromToAction().equals("outsideWalking")) {
		    	distance = edgeObj.get(i).getDistance() * 6;
		    }else {
		    	distance = edgeObj.get(i).getDistance();
		    }
		    		    

		    String coordsListJson = edgeObj.get(i).getPathshape();
		    ObjectMapper mapper = new ObjectMapper();
			JsonNode coordinates = mapper.readTree(coordsListJson);	
			ArrayList<Point> points = new ArrayList<Point>();
//			points.add(new Point()
//					.latitude(edgeObj.get(i).getFromNode().getLatitude())
//					.longitude(edgeObj.get(i).getFromNode().getLongitude()));
//			points.add(new Point()
//					.latitude(edgeObj.get(i).getToNode().getLatitude())
//					.longitude(edgeObj.get(i).getToNode().getLongitude()));
			
			for (int j = 0; j < coordinates.size(); j++) {
				JsonNode coordinateNode = coordinates.get(j);
				Double latitude = coordinateNode.get(1).asDouble();
				Double longitude = coordinateNode.get(0).asDouble();
				
				points.add(new Point()
						.latitude(latitude)
						.longitude(longitude));
			}
//			if(points.size()>2)
//				System.out.println(Arrays.toString(points.toArray()));		    
			nodeList.get(fromNode-1).addEdge(nodeList.get(toNode-1), distance,points);
			//Collections.reverse(points);
			 
			for (int z = 0, j = points.size() - 1; z < j; z++) {
		            points.add(z, points.remove(j));
		        }
			
			//System.out.println(Arrays.toString(points.toArray()));
//			if(points.size()>2)
//				System.out.println("test");
		    nodeList.get(toNode-1).addEdge(nodeList.get(fromNode-1), distance,points);
		    
		    
		    
	    }
	   return nodeList; 
	}

	private static ArrayList<Point> ListPath(long iD, long toID, List<Node> nodeList) {
		System.out.println("From Node "+iD +" To Node "+toID);
		Node currentNode = nodeList.get((int) toID);
		List<Node> path = currentNode.getShortestPath();
		Node PreviousNode=null;
		Double distance =(double) -1;
		ArrayList<Point> listToSend = new ArrayList<Point>();
		ArrayList<double[]> list = new ArrayList<double[]>();
		ArrayList<Point> points = new ArrayList<Point>();
		
		for(Node current : path) {
			if(PreviousNode!=null) {
				for (Entry<Node, Pair> adjacencyPair : PreviousNode.getAdjacentNodes().entrySet()) {
			        if(current.getID() == adjacencyPair.getKey().getID()) {
			        	distance = adjacencyPair.getValue().distance;
			        	points = adjacencyPair.getValue().coords;
			        	
			        	
			        }	          
			    }
				if(PreviousNode.getID()<current.getID())
					Collections.reverse(points);
				listToSend.addAll(points);
				System.out.println("Path from "+PreviousNode.getDescription()+" To "+current.getDescription()+ "With a weight of "+distance+" Size of coords:"+points.size());
				System.out.println(Arrays.toString(points.toArray()));
				System.out.println();
				PreviousNode=current;
			}else {
				PreviousNode = current;
			}
		}
		for (Entry<Node, Pair> adjacencyPair : PreviousNode.getAdjacentNodes().entrySet()) {
	        if(currentNode.getID() == adjacencyPair.getKey().getID()) {
	        	distance = adjacencyPair.getValue().distance;
	        	points = adjacencyPair.getValue().coords;
	        }	          
	    }
		if(PreviousNode.getID()<currentNode.getID())
			Collections.reverse(points);
		listToSend.addAll(points);
		System.out.println("Path from "+PreviousNode.getDescription()+" To "+currentNode.getDescription()+ "With a weight of "+distance+" Size of coords:"+points.size());
		System.out.println(Arrays.toString(points.toArray()));
		return listToSend;
	}
	
	public static List<Point> GetRouteBetweenPoints(long start, long end, List<Node> nodeList){
		CustomGraph graph = new CustomGraph();
	    
	    for(int i=0;i<nodeList.size();i++) {
	    	graph.addNode(nodeList.get(i));
	    }
		System.out.println("Starting routing shortest from NodeID "+start);
	    int found =-1;
	    int found2 =-1;
	    ArrayList<Point> toReturn = new ArrayList<Point>();
	    for(int i=0;i<nodeList.size();i++) {
	    	Node currentNode = nodeList.get(i);
	    	int currentID = currentNode.getID();
	    	if(start == currentID) {
	    		found =currentID;
	    	}
	    }
	    
	    if(found >=0) {
	    	
	    	
	    	Node startNode = nodeList.get(found);
	    	CustomGraph.calculateShortestPathFromSource(graph,startNode);
		    	    	
	    	Scanner obj2 = new Scanner(System.in);
		    
		    obj2.close();
		    
		    for(int i=0;i<nodeList.size();i++) {
		    	Node currentNode = nodeList.get(i);
		    	int currentID = currentNode.getID();
		    	if(end == currentID) {
		    		found2 =currentID;
		    	}
		    }
		    toReturn = ListPath(found,found2,nodeList);
		    //ListPath(found,found2,nodeList);
	    	
	    	
	    	System.out.println(toReturn.size());
	    	
	    }else {
	    	System.out.println("COULDN'T FIND ID");
	    }
	    return toReturn;
	}
	
}













