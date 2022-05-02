package com.capybara.CapybaraCampusCrawlBackend.Routing;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//import com.capybara.CapybaraCampusCrawlBackend.Controllers.routing.Node;
import com.capybara.CapybaraCampusCrawlBackend.Routing.Pair;
import com.capybara.CapybaraCampusCrawlBackend.Models.Point;

public class CapybaraRouteNode{
		private int ID;
		private String description;
		double lat;
		double lon;
		
		private LinkedList<CapybaraRouteNode> shortestPath = new LinkedList<>();
		
		private Double distance = Double.MAX_VALUE;
		
		Map<CapybaraRouteNode,Pair> adjList = new HashMap<>();
		
		public CapybaraRouteNode(int ID, String desc,double lat, double lon) {
	        this.ID = ID;
	        this.description = desc;
	        this.lat = lat;
	        this.lon = lon;
	    }
		
		public void addEdge(CapybaraRouteNode destination, double distance, List<Point> coords) {
			
			adjList.put(destination, new Pair(coords,distance));
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
		
		public static void printADJ(List<CapybaraRouteNode> nodeList) {
		    for(int i=0;i<nodeList.size();i++) {
		    	CapybaraRouteNode currentNode = nodeList.get(i);
		    	System.out.println("Adj List for Node of ID:"+currentNode.getID()+"    Desc:"+currentNode.getDescription());
		    	for (Entry<CapybaraRouteNode, Pair> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
			        CapybaraRouteNode adjacentNode = adjacencyPair.getKey();
			        Double edgeWeigh = adjacencyPair.getValue().distance;
			        System.out.println("        ID:"+adjacentNode.getID()+" Desc:"+adjacentNode.getDescription()+ "  Weight:"+edgeWeigh);
			    }
		    }
		}
		
		public Map<CapybaraRouteNode, Pair> getAdjacentNodes() {
	        return adjList;
	    }

	    public void setAdjacentNodes(Map<CapybaraRouteNode, Pair> adjacentNodes) {
	        this.adjList = adjacentNodes;
	    }
	    
	    public Double getDistance() {
	        return distance;
	    }
	    public void setDistance(Double distance) {
	        this.distance = distance;
	    }
	    public List<CapybaraRouteNode> getShortestPath() {
	        return shortestPath;
	    }
	    public void setShortestPath(LinkedList<CapybaraRouteNode> shortestPath) {
	        this.shortestPath = shortestPath;
	    }
	}