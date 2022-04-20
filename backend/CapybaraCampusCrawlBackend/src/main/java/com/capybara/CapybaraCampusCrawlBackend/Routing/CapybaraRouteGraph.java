package com.capybara.CapybaraCampusCrawlBackend.Routing;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map.Entry;

import com.capybara.CapybaraCampusCrawlBackend.Routing.CapybaraRouteNode;
import com.capybara.CapybaraCampusCrawlBackend.Routing.Pair;

public class CapybaraRouteGraph {

	    private Set<CapybaraRouteNode> nodes = new HashSet<>();
	    
	    public void addNode(CapybaraRouteNode nodeA) {
	        nodes.add(nodeA);
	    }

	    // getters and setters
	    
	    public Set<CapybaraRouteNode> getNodes() {
	        return nodes;
	    }

	    public void setNodes(Set<CapybaraRouteNode> nodes) {
	        this.nodes = nodes;
	    }
	    
	    public static CapybaraRouteGraph calculateShortestPathFromSource(CapybaraRouteGraph graph, CapybaraRouteNode source) {

	        source.setDistance((double) 0);

	        Set<CapybaraRouteNode> settledNodes = new HashSet<>();
	        Set<CapybaraRouteNode> unsettledNodes = new HashSet<>();
	        unsettledNodes.add(source);

	        while (unsettledNodes.size() != 0) {
	            CapybaraRouteNode currentNode = getLowestDistanceNode(unsettledNodes);
	            unsettledNodes.remove(currentNode);
	            for (Entry<CapybaraRouteNode, Pair> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
	                CapybaraRouteNode adjacentNode = adjacencyPair.getKey();
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

	    private static void CalculateMinimumDistance(CapybaraRouteNode evaluationNode, Double edgeWeigh, CapybaraRouteNode sourceNode) {
	        Double sourceDistance = sourceNode.getDistance();
	        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
	            evaluationNode.setDistance(sourceDistance + edgeWeigh);
	            LinkedList<CapybaraRouteNode> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
	            shortestPath.add(sourceNode);
	            evaluationNode.setShortestPath(shortestPath);
	        }
	    }

	    private static CapybaraRouteNode getLowestDistanceNode(Set<CapybaraRouteNode> unsettledNodes) {
	        CapybaraRouteNode lowestDistanceNode = null;
	        double lowestDistance = Double.MAX_VALUE;
	        for (CapybaraRouteNode node : unsettledNodes) {
	            double nodeDistance = node.getDistance();
	            if (nodeDistance < lowestDistance) {
	                lowestDistance = nodeDistance;
	                lowestDistanceNode = node;
	            }
	        }
	        return lowestDistanceNode;
	    }
	    
	}