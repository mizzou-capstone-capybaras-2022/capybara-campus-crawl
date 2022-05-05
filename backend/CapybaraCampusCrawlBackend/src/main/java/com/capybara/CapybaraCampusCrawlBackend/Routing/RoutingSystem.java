package com.capybara.CapybaraCampusCrawlBackend.Routing;

import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.graph.*;
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

import java.util.*;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class RoutingSystem {
	private static final Logger logger = LoggerFactory.getLogger(CapybaraCampusCrawlBackendApplication.class);

	SimpleDirectedWeightedGraph<Long, CapybaraGraphEdge> capybaraGraph;

	@Inject
	public RoutingSystem(GraphEdgeRepository edgeDao, GraphNodeRepository nodeDao) {
		List<GraphNode> nodes = nodeDao.findAll();
		List<GraphEdge> edges = edgeDao.findAll();

		logger.info("Fetch nodes and edges");

		try {
			//current capybara graph does not prefer indoors
			capybaraGraph = constructGraph(nodes, edges, false);
			logger.info("Graph constructed");
		} catch (JsonProcessingException e) {
			capybaraGraph = new SimpleDirectedWeightedGraph<>(CapybaraGraphEdge.class);
		}
	}

	public List<Point> ComputeRoute(Long startingNodeId, Long endingNodeId, boolean preferIndoors) {
		
		//TODO switch graph based on if prefer indoors is true
		SimpleDirectedWeightedGraph<Long, CapybaraGraphEdge> weightedGraphToUse = capybaraGraph;
		
		
		DijkstraShortestPath<Long, CapybaraGraphEdge> dijkstraAlg = new DijkstraShortestPath<>(weightedGraphToUse);
		
		ShortestPathAlgorithm.SingleSourcePaths<Long, CapybaraGraphEdge> pathsFromStart = dijkstraAlg.getPaths(startingNodeId);
		GraphPath<Long, CapybaraGraphEdge> shortestPath = pathsFromStart.getPath(endingNodeId);
		
		List<Point> routePoints = getPathList(shortestPath);

		logger.info(shortestPath.toString());
		return routePoints;
	}

	private List<Point> getPathList(GraphPath<Long, CapybaraGraphEdge> shortestPath){
		List<Point> routePoints = new ArrayList<>();
		for (CapybaraGraphEdge edge: shortestPath.getEdgeList()){
			routePoints.addAll(edge.coords);
		}

		return routePoints;
	}
	
	public SimpleDirectedWeightedGraph<Long, CapybaraGraphEdge> constructGraph(List<GraphNode> nodes, List<GraphEdge> edges, boolean preferIndoors) throws JsonProcessingException {
		SimpleDirectedWeightedGraph<Long, CapybaraGraphEdge> capybaraGraph = new SimpleDirectedWeightedGraph<>(CapybaraGraphEdge.class);

		//Add the vertex of the graph
		for (GraphNode node: nodes){
			Long nodeId = node.getNodeID();
			capybaraGraph.addVertex(nodeId);
		}

		for (GraphEdge edge : edges){
			List<Point> edgeCoords = parseJSONPoints(edge.getPathshape());
			List<Point> reverseCoords = new ArrayList<>(edgeCoords);

			Collections.reverse(reverseCoords);

			double distance = getModifiedGraphEdgeWeight(edge, preferIndoors);
			CapybaraGraphEdge capybaraEdge = new CapybaraGraphEdge(edgeCoords, distance);
			CapybaraGraphEdge reverseCapybaraEdge = new CapybaraGraphEdge(reverseCoords, distance);

			capybaraGraph.addEdge(edge.getFromNode().getNodeID(), edge.getToNode().getNodeID(), capybaraEdge);
			capybaraGraph.addEdge(edge.getToNode().getNodeID(), edge.getFromNode().getNodeID(), reverseCapybaraEdge);

			capybaraGraph.setEdgeWeight(capybaraEdge, capybaraEdge.distance);
			capybaraGraph.setEdgeWeight(reverseCapybaraEdge, reverseCapybaraEdge.distance);
		}

		return capybaraGraph;
	}

	private static double getModifiedGraphEdgeWeight(GraphEdge currentEdge, boolean preferIndoors){
		if(currentEdge.getFromToAction().equals("outsideWalking") && preferIndoors) {
			return currentEdge.getDistance() * 6;
		}else {
			return currentEdge.getDistance();
		}
	}
	private static List<Point> parseJSONPoints(String pointsJson) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		JsonNode coordinates = mapper.readTree(pointsJson);

		ArrayList<Point> points = new ArrayList<Point>();

		for (JsonNode coordinateNode : coordinates) {
			Double latitude = coordinateNode.get(1).asDouble();
			Double longitude = coordinateNode.get(0).asDouble();

			points.add(new Point()
					.latitude(latitude)
					.longitude(longitude));
		}

		return points;
	}

}













