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
import java.util.Scanner;
import java.util.Set;

import com.capybara.CapybaraCampusCrawlBackend.Controllers.generateEdges.Node;

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
	    
	    public Set<Node> getNodes() {
	        return nodes;
	    }

	    public void setNodes(Set<Node> nodes) {
	        this.nodes = nodes;
	    }
	}
	
	public static class Node{
		private int ID;
		private String description;
		double lat;
		double lon;
		
		private LinkedList<Node> shortestPath = new LinkedList<>();
		
		private Double distance = Double.MAX_VALUE;
		
		Map<Node,Double> adjList = new HashMap<>();
		
		public Node(int ID, String desc,double lat, double lon) {
	        this.ID = ID;
	        this.description = desc;
	        this.lat = lat;
	        this.lon = lon;
	    }
		
		public void addEdge(Node destination, double distance) {
			adjList.put(destination, distance);
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

	    public void setAdjacentNodes(Map<Node, Double> adjacentNodes) {
	        this.adjList = adjacentNodes;
	    }
	    
	    public Double getDistance() {
	        return distance;
	    }
	    public void setDistance(Double distance) {
	        this.distance = distance;
	    }
	    public List<Node> getShortestPath() {
	        return shortestPath;
	    }
	    public void setShortestPath(LinkedList<Node> shortestPath) {
	        this.shortestPath = shortestPath;
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
		    double distance = edgeObj.get(i).get("distance").asDouble();
		    
		    //System.out.println("From Node ID:"+fromNode+"  toNode ID:"+toNode+" distance:"+distance+"\r\n"+"From Desc:"+nodeList.get(fromNode-1).getDescription()+"    to Desc:"+nodeList.get(toNode-1).getDescription()+"\r\n\r\n");
		    
		    //add edges based on distance
		    
		     
		    //nodeList.get(fromNode-1).addEdge(nodeList.get(toNode-1), distance,coordinates);
		    //nodeList.get(fromNode-1).addEdge(nodeList.get(toNode-1), distance,coordinatesReversed);
		    nodeList.get(fromNode-1).addEdge(nodeList.get(toNode-1), distance);
		    nodeList.get(toNode-1).addEdge(nodeList.get(fromNode-1), distance);
	    }
	    
	    System.out.println("Starting ORS routing");
	    //TO BE DELTED BEFORE DEPLOYING. MUST HAVE ORS RUNNING WITH SPECIFIC CONFIGURATIONS TO WORK 
	    
	    for(int i =0;i<nodeObj.size();i++) {
	    	//Starting point
	    	JsonNode fromJSON = nodeObj.get(i);
    		
	    	String fromName=fromJSON.get("description").asText();
	    	int fromID=fromJSON.get("nodeID").asInt();
	    	for(int j =i+1;j<nodeObj.size();j++) {
	    	//for(int j =0;j<nodeObj.size();j++) {
	    		JsonNode toJSON = nodeObj.get(j);
	    		
	    		String toName=toJSON.get("description").asText();  		
	    		String toID=toJSON.get("nodeID").asText();
	    		if(j!=i) {
	    		//end point
	    		
	    		
	    		//if inside route
	    		if((fromName.toLowerCase().startsWith("door") &&toName.toLowerCase().startsWith("door"))&&(fromName.substring(11).equals(toName.substring(11)))) {
	    			
	    			//System.out.println("From: "+fromName.substring(11)+"To: "+toName.substring(11));
	    			//System.out.println("two doors!!");
	    		}else {
	    			
	    			//coordinates for starting point
	    			Double lat1=fromJSON.get("latitude").asDouble();
	    			Double lon1=fromJSON.get("longitude").asDouble();
	    			
	    			//coordinates for end point
	    			Double lat2=toJSON.get("latitude").asDouble();
	    			Double lon2=toJSON.get("longitude").asDouble();
	    			
	    			//DATA CORRECTION, SHOULD BE FIXED ONCE DATABASE IS RE-SEEDED
	    			if(lon1 >50)
	    				lon1 = lon1*-1;
	    			if(lon2 >50)
	    				lon2 = lon2*-1;
	    			
	    			String request ="";
	    			//System.out.println("lat1: "+lat1+" lon1: "+lon1+"lat2: "+lat2+" lon2: "+lon2);
	    			
	    			//MORE DATA CORRECTION
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
	    			
	    			//JSON of the result from the ORS call
	    			JsonNode jsonrequest = mapper.readTree(requestString);
	    			//System.out.println("FROM: "+fromID+" TO: "+toID);
	    			//System.out.println("FROM: "+fromName+" TO: "+toName+"\r\nJSON:\r\n"+jsonrequest.toPrettyString());
	    			Double distance = jsonrequest.get("features").get(0).get("properties").get("segments").get(0).get("distance").asDouble();
	    			
	    			distance = distance *1.3;
	    			//change distance here to prioritize inside walking cuz of rain, other preferences
	    			
	    			//System.out.println(distance);
	    			//System.out.println(distance);
	    			
	    			int fromNode = fromJSON.get("nodeID").asInt();
	    		    int toNode = toJSON.get("nodeID").asInt();
	    		    //System.out.println("creating ORS edge from: "+fromNode+" To: "+toNode);
	    		    
	    		    //if negative or 0 distance
	    		    if(distance >0) {
	    		    	nodeList.get(fromNode-1).addEdge(nodeList.get(toNode-1), distance);
		    		    nodeList.get(toNode-1).addEdge(nodeList.get(fromNode-1), distance);	
	    		    }
	    			
	    		}
	    		//System.out.println("From:"+fromJSON.get("description").asText()+"To:"+toJSON.get("description").asText());
	    		}else {
	    			System.out.println("From:"+fromID+" TO:"+toID);
	    		}
	    	}
	    	//int left = nodeObj.size()-fromID
	    	System.out.println(fromID+" is done mapping Left:"+ (nodeObj.size()-fromID));
	    }
	    //END OF WHAT SHOULD BE DELETED ONCE ORS GETS ADDED TO DATABASE
	    //once it is added, the node in a data
	    
//prints the adj list of all the nodes. Used for testing purposes
/*	    
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
*/	    
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
	    	long start = System.nanoTime();
	    	Node startNode = nodeList.get(found);
	    	Graph graph2 = calculateShortestPathFromSource(graph,startNode);
		    long end = System.nanoTime();
		    long duration = (end-start)/1000000;
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
		    ListShortestPath(found,found2,nodeList);
	    	
	    	
	    	
	    	
	    	
	    }else {
	    	System.out.println("COULDN'T FIND ID");
	    }
	    
	    System.out.println("test");
	    
	    
	}

private static void ListShortestPath(int iD, int toID, List<Node> nodeList) {
		// TODO Auto-generated method stub
		System.out.println("From Node "+iD +" To Node "+toID);
		Node currentNode = nodeList.get(toID);
		List<Node> path = currentNode.getShortestPath();
		Node PreviousNode=null;
		Double distance =(double) -1;
		
		for(Node current : path) {
			if(PreviousNode!=null) {
				for (Entry<Node, Double> adjacencyPair : PreviousNode.getAdjacentNodes().entrySet()) {
			        if(current.getID() == adjacencyPair.getKey().getID()) {
			        	distance = adjacencyPair.getValue();
			        }	          
			    }
				System.out.println("Path from "+PreviousNode.getID()+" To "+current.getID()+ "With a weight of "+distance);
				PreviousNode=current;
			}else {
				PreviousNode = current;
			}
		}
		for (Entry<Node, Double> adjacencyPair : PreviousNode.getAdjacentNodes().entrySet()) {
	        if(currentNode.getID() == adjacencyPair.getKey().getID()) {
	        	distance = adjacencyPair.getValue();
	        }	          
	    }
		System.out.println("Path from "+PreviousNode.getID()+" To "+currentNode.getID()+ "With a weight of "+distance);
		
	}



//	public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
//		source
//	}



	//algorithms
	public static Graph calculateShortestPathFromSource(Graph graph, Node source) {

        source.setDistance((double) 0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Entry<Node, Double> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Double edgeWeigh = adjacencyPair.getValue();

                if (!settledNodes.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeigh, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private static void CalculateMinimumDistance(Node evaluationNode, Double edgeWeigh, Node sourceNode) {
        Double sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        double lowestDistance = Double.MAX_VALUE;
        for (Node node : unsettledNodes) {
            double nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }




















	
	
	
	
	
	
}