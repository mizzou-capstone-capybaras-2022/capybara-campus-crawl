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
	
	
	
	public static void main(String arg[]) throws Exception{
		
		System.out.println("GET requests");
		long start = System.nanoTime();
		String nodeString = getRequest("http://localhost:8090/graph-nodes/");
	    //String edgeString = getRequest("http://localhost:8090/graph-edges/");
	    File myObj = new File("edges.txt");
	    Scanner myReader = new Scanner(myObj);
	    String edgeString = myReader.nextLine();
	    //System.out.println(edgeString);
//	    FileWriter myWriter = new FileWriter("edges.txt");
//	    myWriter.write(edgeString);
//	    myWriter.close();
	    
	    long end = System.nanoTime();
	    long duration = (end-start)/1000000;
    	System.out.println("finished calculating: Took "+duration);
		
	    System.out.println("end requests");
	    
	    //turn into JSON object and pretty print
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode nodeObj = mapper.readTree(nodeString);
	    JsonNode edgeObj = mapper.readTree(edgeString);
	    
	    //String nodepretty = nodeObj.toPrettyString();
	    //String edgepretty = edgeObj.toPrettyString();
	    //System.out.print(nodepretty);
	    //System.out.print(edgepretty);
	    
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
		    double distance;
		    if(edgeObj.get(i).get("fromToAction").asText().equals("outsideWalking")) {
		    	distance = edgeObj.get(i).get("distance").asDouble() * 1.3;
		    }else {
		    	distance = edgeObj.get(i).get("distance").asDouble();
		    }
		    
		    //System.out.println("From Node ID:"+fromNode+"  toNode ID:"+toNode+" distance:"+distance+"\r\n"+"From Desc:"+(fromNode-1).getDescription()+"    to Desc:"+nodeList.get(toNode-1).getDescription()+"\r\n\r\n");
		    
//		    ArrayList<double[]> listzoom = new ArrayList<double[]>();
//		    ArrayNode array = (ArrayNode) edgeObj.get(i).get("coordinates")
//	    	if(array.isArray()) {
//	    	int count=0;
//	    	for (final JsonNode objNode : array) {
//	    		double[] test = {objNode.get(0).asDouble(),objNode.get(1).asDouble()};
//	    		list.add(test);	    		
//	    		count++;
//	        }
//	    }
		    
//DELETE WHEN COORDS GET ADDED INTO JSON

		    
		    Double lon1=edgeObj.get(i).get("fromNode").get("longitude").asDouble();
		    Double lat1=edgeObj.get(i).get("fromNode").get("latitude").asDouble();
		    Double lon2=edgeObj.get(i).get("toNode").get("longitude").asDouble();
		    Double lat2=edgeObj.get(i).get("toNode").get("latitude").asDouble();
		    double[] first = {lon1,lat1};
		    double[] second = {lon2,lat2};
		    ArrayList<double[]> list = new ArrayList<double[]>();
		    list.add(first);
		    list.add(second);
		    nodeList.get(fromNode-1).addEdge(nodeList.get(toNode-1), distance,list);
		    Collections.reverse(list);
		    nodeList.get(toNode-1).addEdge(nodeList.get(fromNode-1), distance,list);
	    }
	    
	    
	    
//prints the adj list of all the nodes. Used for testing purposes  
//	    int count =0;
//	    for(int i=0;i<nodeList.size();i++) {
//	    	Node currentNode = nodeList.get(i);
//	    	System.out.println("Adj List for Node of ID:"+currentNode.getID()+"    Desc:"+currentNode.getDescription());
//	    	for (Entry<Node, Pair> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
//		        Node adjacentNode = adjacencyPair.getKey();
//		        Double edgeWeigh = adjacencyPair.getValue().distance;
//		        count ++;
//		        System.out.println("        ID:"+adjacentNode.getID()+" Desc:"+adjacentNode.getDescription()+ "  Weight:"+edgeWeigh);
//		    }
//	    } 
//	    //System.out.println(count);
	    
	    //cerates graph for algorithm
	    Graph graph = new Graph();
	    for(int i=0;i<nodeList.size();i++) {
	    	graph.addNode(nodeList.get(i));
	    }
	    
	    Scanner obj = new Scanner(System.in);
	    System.out.println("Enter From ID:");
	    int ID = Integer.parseInt(obj.nextLine());
	    int found =-1;
	    
	    //calculating shortest path from Node that user specifies
	    System.out.println("Starting routing shortest from NodeID "+ID);
	    for(int i=0;i<nodeList.size();i++) {
	    	Node currentNode = nodeList.get(i);
	    	int currentID = currentNode.getID();
	    	if(ID == currentID) {
	    		found =currentID;
	    	}
	    }
	    
	    if(found >=0) {
	    	start = System.nanoTime();
	    	Node startNode = nodeList.get(found);
	    	Graph.calculateShortestPathFromSource(graph,startNode);
		    end = System.nanoTime();
		    duration = (end-start)/1000000;
	    	System.out.println("finished calculating: Took "+duration);
	    	
	    	Scanner obj2 = new Scanner(System.in);
		    System.out.println("Enter to ID:");
		    int toID = Integer.parseInt(obj2.nextLine());
		    int found2 =-1;
		    for(int i=0;i<nodeList.size();i++) {
		    	Node currentNode = nodeList.get(i);
		    	int currentID = currentNode.getID();
		    	if(toID == currentID) {
		    		found2 =currentID;
		    	}
		    }
		    ListPath(found,found2,nodeList);
	    	
	    	
	    	
	    	
	    	
	    }else {
	    	System.out.println("COULDN'T FIND ID");
	    }
	    
	    System.out.println("test");
	    
	    
	}

private static ArrayList<double[]> ListPath(int iD, int toID, List<Node> nodeList) {
		System.out.println("From Node "+iD +" To Node "+toID);
		Node currentNode = nodeList.get(toID);
		List<Node> path = currentNode.getShortestPath();
		Node PreviousNode=null;
		Double distance =(double) -1;
		ArrayList<double[]> listToSend = new ArrayList<double[]>();
		ArrayList<double[]> list = new ArrayList<double[]>();
		
		for(Node current : path) {
			if(PreviousNode!=null) {
				for (Entry<Node, Pair> adjacencyPair : PreviousNode.getAdjacentNodes().entrySet()) {
			        if(current.getID() == adjacencyPair.getKey().getID()) {
			        	distance = adjacencyPair.getValue().distance;
			        	list = adjacencyPair.getValue().coords;
			        }	          
			    }
				listToSend.addAll(list);
				System.out.println("Path from "+PreviousNode.getDescription()+" To "+current.getDescription()+ "With a weight of "+distance+" Size of coords:"+list.size());
				PreviousNode=current;
			}else {
				PreviousNode = current;
			}
		}
		for (Entry<Node, Pair> adjacencyPair : PreviousNode.getAdjacentNodes().entrySet()) {
	        if(currentNode.getID() == adjacencyPair.getKey().getID()) {
	        	distance = adjacencyPair.getValue().distance;
	        	list = adjacencyPair.getValue().coords;
	        }	          
	    }
		System.out.println("Path from "+PreviousNode.getDescription()+" To "+currentNode.getDescription()+ "With a weight of "+distance+" Size of coords:"+list.size());
		return listToSend;
	}	
}






















