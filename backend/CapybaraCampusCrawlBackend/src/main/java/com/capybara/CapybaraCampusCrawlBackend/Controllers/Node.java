package com.capybara.CapybaraCampusCrawlBackend.Controllers;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//import com.capybara.CapybaraCampusCrawlBackend.Controllers.routing.Node;
import com.capybara.CapybaraCampusCrawlBackend.Controllers.Pair;
import com.capybara.CapybaraCampusCrawlBackend.Models.Point;

public class Node{
		private int ID;
		private String description;
		double lat;
		double lon;
		
		private LinkedList<Node> shortestPath = new LinkedList<>();
		
		private Double distance = Double.MAX_VALUE;
		
		Map<Node,Pair> adjList = new HashMap<>();
		
		public Node(int ID, String desc,double lat, double lon) {
	        this.ID = ID;
	        this.description = desc;
	        this.lat = lat;
	        this.lon = lon;
	    }
		
		public void addEdge(Node destination, double distance, ArrayList<Point> coords) {
			
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
		public Map<Node, Pair> getAdjacentNodes() {
	        return adjList;
	    }

	    public void setAdjacentNodes(Map<Node, Pair> adjacentNodes) {
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