package com.capybara.CapybaraCampusCrawlBackend.Controllers;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map.Entry;

import com.capybara.CapybaraCampusCrawlBackend.Controllers.Graph;
import com.capybara.CapybaraCampusCrawlBackend.Controllers.Node;
import com.capybara.CapybaraCampusCrawlBackend.Controllers.Pair;

public class Graph {

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
	    
	    
	    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {

	        source.setDistance((double) 0);

	        Set<Node> settledNodes = new HashSet<>();
	        Set<Node> unsettledNodes = new HashSet<>();
	        unsettledNodes.add(source);

	        while (unsettledNodes.size() != 0) {
	            Node currentNode = getLowestDistanceNode(unsettledNodes);
	            unsettledNodes.remove(currentNode);
	            for (Entry<Node, Pair> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
	                Node adjacentNode = adjacencyPair.getKey();
	                Double edgeWeigh = adjacencyPair.getValue().distance;

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