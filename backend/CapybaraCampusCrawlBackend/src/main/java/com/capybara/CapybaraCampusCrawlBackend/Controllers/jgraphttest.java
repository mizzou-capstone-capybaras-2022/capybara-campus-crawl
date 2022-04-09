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
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.tomcat.util.json.JSONParser;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.junit.*;

public class jgraphttest{
	
	public static void main(String arg[]) throws Exception{

        SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>  graph = 
        new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>
        (DefaultWeightedEdge.class); 
        graph.addVertex("vertex1");
        graph.addVertex("vertex2");
        graph.addVertex("vertex3");
        graph.addVertex("vertex4");
        graph.addVertex("vertex5");


        DefaultWeightedEdge e1 = graph.addEdge("vertex1", "vertex2"); 
        graph.setEdgeWeight(e1, 5); 

        DefaultWeightedEdge e2 = graph.addEdge("vertex2", "vertex3"); 
        graph.setEdgeWeight(e2, 3); 

        DefaultWeightedEdge e3 = graph.addEdge("vertex4", "vertex5"); 
        graph.setEdgeWeight(e3, 6); 

        DefaultWeightedEdge e4 = graph.addEdge("vertex2", "vertex4"); 
        graph.setEdgeWeight(e4, 2); 

        DefaultWeightedEdge e5 = graph.addEdge("vertex5", "vertex4"); 
        graph.setEdgeWeight(e5, 4); 


        DefaultWeightedEdge e6 = graph.addEdge("vertex2", "vertex5"); 
        graph.setEdgeWeight(e6, 9); 

        DefaultWeightedEdge e7 = graph.addEdge("vertex4", "vertex1"); 
        graph.setEdgeWeight(e7, 7); 

        DefaultWeightedEdge e8 = graph.addEdge("vertex3", "vertex2"); 
        graph.setEdgeWeight(e8, 2); 

        DefaultWeightedEdge e9 = graph.addEdge("vertex1", "vertex3"); 
        graph.setEdgeWeight(e9, 10); 

        DefaultWeightedEdge e10 = graph.addEdge("vertex3", "vertex5"); 
        graph.setEdgeWeight(e10, 1); 


        System.out.println("Shortest path from vertex1 to vertex5:");
        GraphPath<String, DefaultWeightedEdge> shortest_path =   DijkstraShortestPath.findPathBetween(graph, "vertex1", "vertex5");
        System.out.println(shortest_path);

    }
}














