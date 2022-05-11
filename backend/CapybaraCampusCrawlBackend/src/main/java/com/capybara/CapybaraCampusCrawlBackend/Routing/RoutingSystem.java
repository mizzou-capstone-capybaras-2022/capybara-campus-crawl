package com.capybara.CapybaraCampusCrawlBackend.Routing;

import com.capybara.CapybaraCampusCrawlBackend.CapybaraCampusCrawlBackendApplication;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.GraphEdgeRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.GraphNodeRepository;
import com.capybara.CapybaraCampusCrawlBackend.Models.GraphEdge;
import com.capybara.CapybaraCampusCrawlBackend.Models.GraphNode;
import com.capybara.CapybaraCampusCrawlBackend.Models.Point;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class RoutingSystem {
	private static final Logger logger = LoggerFactory.getLogger(CapybaraCampusCrawlBackendApplication.class);

	CapybaraGraph capybaraGraph;

	@Inject
	public RoutingSystem(GraphEdgeRepository edgeDao, GraphNodeRepository nodeDao) {
		List<GraphNode> nodes = nodeDao.findAll();
		List<GraphEdge> edges = edgeDao.findAll();

		logger.info("Fetch nodes and edges");

		try {
			//current capybara graph does not prefer indoors
			capybaraGraph = constructGraph(nodes, edges);
			logger.info("Graph constructed");
		} catch (JsonProcessingException e) {
			capybaraGraph = new CapybaraGraph();
		}
	}

	public List<Point> ComputeRoute(Long startingNodeId, Long endingNodeId, boolean preferIndoors) {
		GraphPath<Long, CapybaraGraphEdge> shortestPath = ComputeShortestCapybaraPath(startingNodeId, endingNodeId, preferIndoors);
		List<Point> routePoints = getPathList(shortestPath);
		return routePoints;
	}

	public Double ComputeRouteDistance(Long startingNodeId, Long endingNodeId, boolean preferIndoors){
		GraphPath<Long, CapybaraGraphEdge> shortestPath = ComputeShortestCapybaraPath(startingNodeId, endingNodeId, preferIndoors);
		return shortestPath.getWeight();
	}

	private GraphPath<Long, CapybaraGraphEdge> ComputeShortestCapybaraPath(Long startingNodeId, Long endingNodeId, boolean preferIndoors){
		capybaraGraph.setPreferIndoors(preferIndoors);
		DijkstraShortestPath<Long, CapybaraGraphEdge> dijkstraAlg = new DijkstraShortestPath<>(capybaraGraph);

		ShortestPathAlgorithm.SingleSourcePaths<Long, CapybaraGraphEdge> pathsFromStart = dijkstraAlg.getPaths(startingNodeId);
		GraphPath<Long, CapybaraGraphEdge> shortestPath = pathsFromStart.getPath(endingNodeId);

		logger.info(shortestPath.toString());

		return shortestPath;
	}

	private List<Point> getPathList(GraphPath<Long, CapybaraGraphEdge> shortestPath){
		List<Point> routePoints = new ArrayList<>();
		for (CapybaraGraphEdge edge: shortestPath.getEdgeList()){
			routePoints.addAll(edge.coords);
		}

		return routePoints;
	}
	
	public CapybaraGraph constructGraph(List<GraphNode> nodes, List<GraphEdge> edges) throws JsonProcessingException {
		CapybaraGraph capybaraGraph = new CapybaraGraph();

		//Add the vertex of the graph
		for (GraphNode node: nodes){
			Long nodeId = node.getNodeID();
			capybaraGraph.addVertex(nodeId);
		}

		for (GraphEdge edge : edges){
			Long nodeAId = edge.getFromNode().getNodeID();
			Long nodeBId = edge.getToNode().getNodeID();

			List<Point> edgeCoords = parseJSONPoints(edge.getPathshape());
			boolean indoorEdge = edge.getFromToAction().equals("outsideWalking");
			CapybaraGraphEdge capybaraEdge = new CapybaraGraphEdge(edgeCoords, indoorEdge, edge.getDistance());

			capybaraGraph.addEdge(nodeAId, nodeBId, capybaraEdge);
			capybaraGraph.addEdge(nodeBId, nodeAId, capybaraEdge.getReverseEdge());
		}

		return capybaraGraph;
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













