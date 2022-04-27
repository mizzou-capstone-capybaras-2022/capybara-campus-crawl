package com.capybara.CapybaraCampusCrawlBackend.Controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import com.capybara.CapybaraCampusCrawlBackend.Controllers.Node;
import com.capybara.CapybaraCampusCrawlBackend.Models.Point;

//import org.apache.commons.logging.Log;
//import org.apache.tomcat.util.json.JSONParser;
//import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import org.jgrapht.*;


public class routing{
	
	public static void main(String arg[]) throws Exception{
		
		System.out.println("GET requests");
		//long start = System.nanoTime();
		String nodeString = getRequest("http://localhost:8090/graph-nodes/");
	    //String edgeString = getRequest("http://localhost:8090/graph-edges/");
	    File myObj = new File("edges.txt");
	    Scanner myReader = new Scanner(myObj);
	    String edgeString = myReader.nextLine();
	    myReader.close();
	    //long end = System.nanoTime();
	    //long duration = (end-start)/1000000;
    	//System.out.println("finished calculating: Took "+duration);
	    System.out.println("end requests");
	    
	    //turn into JSON object
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode nodeObj = mapper.readTree(nodeString);
	    JsonNode edgeObj = mapper.readTree(edgeString);
	    
	    List<Node> nodeList = generateNodes(nodeObj,edgeObj);
	    //printADJ(nodeList);
	    List<Node> blankList = generateNodes(nodeObj,edgeObj);

	    
	    //creates graph for algorithm
	    
	    
	    Scanner obj = new Scanner(System.in);
	    System.out.println("Enter From ID:");
	    int ID = Integer.parseInt(obj.nextLine());
	    
	    System.out.println("Enter to ID:");
	    int toID = Integer.parseInt(obj.nextLine());
	    obj.close();

	    GetRouteBetweenPoints(ID, toID, nodeList);
	    	    
	    System.out.println("test");
	    
	    
	}
	
	public static List<Point> GetRouteBetweenPoints(int start, int end, List<Node> nodeList){
		Graph graph = new Graph();
	    
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
	    	Graph.calculateShortestPathFromSource(graph,startNode);
		    	    	
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
	
	private static List<Node> generateNodes(JsonNode nodeObj, JsonNode edgeObj) throws JsonMappingException, JsonProcessingException{
		List<Node> nodeList = new ArrayList<Node>();
		//generate nodes
	    for(int i =0;i<nodeObj.size();i++) {
	    	JsonNode currentJSON = nodeObj.get(i);
	    	
	    	int nodeID = currentJSON.get("nodeID").asInt()-1;
	    	String nodeDesc = currentJSON.get("description").asText();
	    	double lat = currentJSON.get("latitude").asDouble();
	    	double lon = currentJSON.get("longitude").asDouble();
	    	
	    	Node node = new Node(nodeID,nodeDesc,lat,lon);
	    	nodeList.add(node);
	    }
	    
	    System.out.println("Starting inside routing");
	    //generate adj lists for every node
	    for(int i=0;i<edgeObj.size();i++) {
		    int fromNode = edgeObj.get(i).get("fromNode").get("nodeID").asInt();
		    int toNode = edgeObj.get(i).get("toNode").get("nodeID").asInt();
		    
		    //get distance and modify for outside
		    double distance;
		    if(edgeObj.get(i).get("fromToAction").asText().equals("outsideWalking")) {
		    	distance = edgeObj.get(i).get("distance").asDouble() * 6;
		    }else {
		    	distance = edgeObj.get(i).get("distance").asDouble();
		    }
		    		    

		    String coordsListJson = edgeObj.get(i).get("pathshape").asText();
		    ObjectMapper mapper = new ObjectMapper();
			JsonNode coordinates = mapper.readTree(coordsListJson);	
			ArrayList<Point> points = new ArrayList<Point>();
			
			for (int j = 0; j < coordinates.size(); j++) {
				JsonNode coordinateNode = coordinates.get(j);
				Double latitude = coordinateNode.get(1).asDouble();
				Double longitude = coordinateNode.get(0).asDouble();
				
				points.add(new Point()
						.latitude(latitude)
						.longitude(longitude));
			}
			
		    nodeList.get(fromNode-1).addEdge(nodeList.get(toNode-1), distance,points);
		    Collections.reverse(points);
		    nodeList.get(toNode-1).addEdge(nodeList.get(fromNode-1), distance,points);
	    }
	   return nodeList; 
	}

	private static ArrayList<Point> ListPath(int iD, int toID, List<Node> nodeList) {
		System.out.println("From Node "+iD +" To Node "+toID);
		Node currentNode = nodeList.get(toID);
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
				listToSend.addAll(points);
				System.out.println("Path from "+PreviousNode.getDescription()+" To "+current.getDescription()+ "With a weight of "+distance+" Size of coords:"+points.size());
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
		listToSend.addAll(points);
		System.out.println("Path from "+PreviousNode.getDescription()+" To "+currentNode.getDescription()+ "With a weight of "+distance+" Size of coords:"+points.size());
		return listToSend;
	}	

	public static String getRequest(String url) throws Exception{
		BufferedReader reader;
	    String line;
	    StringBuffer responseContent = new StringBuffer();
	    
	    URL url2 = new URL(url);
	    HttpURLConnection http = (HttpURLConnection)url2.openConnection();
	    http.setRequestMethod("GET");

	    if(http.getResponseCode() >299) {
	    	System.out.println("!!!!ERROR :" + http.getResponseCode() );
	    }else {
	    	reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
	    	while((line = reader.readLine())!=null) {
	    		responseContent.append(line);
	    	}
	    }
	    return responseContent.toString();
	}
}
























