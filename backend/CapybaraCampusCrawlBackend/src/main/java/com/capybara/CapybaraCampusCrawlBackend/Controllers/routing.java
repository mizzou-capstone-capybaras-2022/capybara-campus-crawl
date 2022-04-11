package com.capybara.CapybaraCampusCrawlBackend.Controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

//import org.apache.commons.logging.Log;
//import org.apache.tomcat.util.json.JSONParser;
//import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import org.jgrapht.*;


public class routing{
  
	public static class Graph {

	    private Set<Node> nodes = new HashSet<>();
	    
	    public void addNode(Node nodeA) {
	        nodes.add(nodeA);
	    }

	    // getters and setters 
	}
	
	public static class Node{
		private int ID;
		private String description;
		double lat;
		double lon;
		private List<Node> shortestPath = new LinkedList<>();
		private Integer distance = Integer.MAX_VALUE;
		Map<Node,Double> adjList = new HashMap<>();
		
		public void addEdge(Node destination, double distance) {
			adjList.put(destination, distance);
	    }
		
		public Node(int ID, String desc,double lat, double lon) {
	        this.ID = ID;
	        this.description = desc;
	        this.lat = lat;
	        this.lon = lon;
	    }
		public int getID() {
			return ID;
		}
		public void setID(int ID) {
			this.ID = ID;
		}
		
		public String getDescription() {
			return description;
		}
		public Map<Node, Double> getAdjacentNodes() {
	        return adjList;
	    }
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
	
	
	
	public static void main(String arg[]) throws Exception{
		System.out.println("GET requests");
		String nodeString = getRequest("http://localhost:8090/graph-nodes/");
	    String edgeString = getRequest("http://localhost:8090/graph-edges/");
	    //System.out.print(responseContent.toString());
	    System.out.println("end requests");
	    //turn into JSON object and pretty print
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode nodeObj = mapper.readTree(nodeString);
	    JsonNode edgeObj = mapper.readTree(edgeString);
	    //String nodepretty = nodeObj.toPrettyString();
	    //String edgepretty = edgeObj.toPrettyString();
	    //System.out.print(nodepretty);
	    //System.out.print(edgepretty);
	    
	    //creating node objects
	    
	    List<Node> nodeList = new ArrayList<Node>();
	    //generate nodes
	    for(int i =0;i<nodeObj.size();i++) {
	    	JsonNode currentJSON = nodeObj.get(i);
	    	int nodeID = currentJSON.get("nodeID").asInt();
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
		    double distance = edgeObj.get(i).get("distance").asDouble();
		    
		    //System.out.println("From Node ID:"+fromNode+"  toNode ID:"+toNode+" distance:"+distance+"\r\n"+"From Desc:"+nodeList.get(fromNode-1).getDescription()+"    to Desc:"+nodeList.get(toNode-1).getDescription()+"\r\n\r\n");
		    //add nodes based on a specific distance
		    nodeList.get(fromNode-1).addEdge(nodeList.get(toNode-1), distance);
		    nodeList.get(toNode-1).addEdge(nodeList.get(fromNode-1), distance);
	    }
	    
	    System.out.println("Starting ORS routing");
	    for(int i =0;i<nodeObj.size();i++) {
	    	JsonNode fromJSON = nodeObj.get(i);
	    	String fromID=fromJSON.get("nodeID").asText();
	    	for(int j =i+1;j<nodeObj.size();j++) {
	    		//System.out.println(fromJSON.toPrettyString());
	    		
	    		JsonNode toJSON = nodeObj.get(j);
	    		//System.out.println(toJSON.toPrettyString());
	    		String fromName=fromJSON.get("description").asText();
	    		String toName=toJSON.get("description").asText();
	    		
	    		String toID=toJSON.get("nodeID").asText();
	    		if((fromName.toLowerCase().startsWith("door") &&toName.toLowerCase().startsWith("door"))&&(fromName.substring(11).equals(toName.substring(11)))) {
	    			
	    			//System.out.println("From: "+fromName.substring(11)+"To: "+toName.substring(11));
	    			//System.out.println("two doors!!");
	    		}else {
	    			
	    			//make openrouteservice call from fromJSON to toJSON
	    			Double lat1=fromJSON.get("latitude").asDouble();
	    			Double lon1=fromJSON.get("longitude").asDouble();
	    			Double lat2=toJSON.get("latitude").asDouble();
	    			Double lon2=toJSON.get("longitude").asDouble();
	    			if(lon1 >50)
	    				lon1 = lon1*-1;
	    			if(lon2 >50)
	    				lon2 = lon2*-1;
	    			String request ="";
	    			//System.out.println("lat1: "+lat1+" lon1: "+lon1+"lat2: "+lat2+" lon2: "+lon2);
	    			if(lat1 <0) {
	    				if(lat2<0) {//switch both lats and lons
	    					request = "http://localhost:8080/ors/v2/directions/foot-walking?start="+lat1+","+lon1+"&end="+lat2+","+lon2;
	    				}else {//only switch lat1 and lon1
	    					request = "http://localhost:8080/ors/v2/directions/foot-walking?start="+lat1+","+lon1+"&end="+lon2+","+lat2;
	    				}
	    			}else {
	    				if(lat2<0) {//only switch lat2 and lon2
	    					request = "http://localhost:8080/ors/v2/directions/foot-walking?start="+lon1+","+lat1+"&end="+lat2+","+lon2;
	    				}
	    				else {//switch none
	    					request = "http://localhost:8080/ors/v2/directions/foot-walking?start="+lon1+","+lat1+"&end="+lon2+","+lat2;
	    				}
	    			}
	    			//String request = "http://localhost:8080/ors/v2/directions/foot-walking?start="+lon1+","+lat1+"&end="+lon2+","+lat2; 
	    			//System.out.println(request);
	    			String requestString = getRequest(request);
	    			JsonNode jsonrequest = mapper.readTree(requestString);
	    			//System.out.println("FROM: "+fromID+" TO: "+toID);
	    			//System.out.println("FROM: "+fromName+" TO: "+toName+"\r\nJSON:\r\n"+jsonrequest.toPrettyString());
	    			Double distance = jsonrequest.get("features").get(0).get("properties").get("segments").get(0).get("distance").asDouble();
	    			//System.out.println(distance);
	    			//System.out.println(distance);
	    			int fromNode = fromJSON.get("nodeID").asInt();
	    		    int toNode = toJSON.get("nodeID").asInt();
	    		    //System.out.println("creating ORS edge from: "+fromNode+" To: "+toNode);
	    		    if(distance >0) {
	    		    	nodeList.get(fromNode-1).addEdge(nodeList.get(toNode-1), distance);
		    		    nodeList.get(toNode-1).addEdge(nodeList.get(fromNode-1), distance);	
	    		    }
	    			
	    		}
	    		//System.out.println("From:"+fromJSON.get("description").asText()+"To:"+toJSON.get("description").asText());
	    	}
	    	System.out.println(fromID+" is done mapping");
	    }
	    
	    int count =0;
	    for(int i=0;i<nodeList.size();i++) {
	    	Node currentNode = nodeList.get(i);
	    	System.out.println("Adj List for Node of ID:"+currentNode.getID()+"    Desc:"+currentNode.getDescription());
	    	for (Entry<Node, Double> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
		          Node adjacentNode = adjacencyPair.getKey();
		          Double edgeWeigh = adjacencyPair.getValue();
		          count ++;
		          System.out.println("        ID:"+adjacentNode.getID()+" Desc:"+adjacentNode.getDescription()+ "  Weight:"+edgeWeigh);
		    }
	    }
 
	    System.out.println(count);
	    
	    Graph graph = new Graph();
	    for(int i=0;i<nodeList.size();i++) {
	    	graph.addNode(nodeList.get(i));
	    }
	   
	    
	    
	    
	}

//	public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
//		source
//	}


}





















