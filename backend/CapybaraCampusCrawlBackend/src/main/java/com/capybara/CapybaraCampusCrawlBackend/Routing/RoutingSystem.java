package com.capybara.CapybaraCampusCrawlBackend.Routing;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class RoutingSystem {

	private static final Logger logger = LoggerFactory.getLogger(CapybaraCampusCrawlBackendApplication.class);
	
	
	private GraphEdgeRepository edgeDao;
	
	private GraphNodeRepository nodeDao;
	
	private Graph<Long,CapybaraGraphEdgeForRouting> mizzouGraph;
	
	private HashMap<Long, GraphNode> graphNodeLookupTable = new HashMap<>();
	
	@Inject
	public RoutingSystem(GraphEdgeRepository edgeDao, GraphNodeRepository nodeDao) {
		this.edgeDao = edgeDao;
		this.nodeDao = nodeDao;
		
		mizzouGraph = new DefaultUndirectedWeightedGraph<Long, CapybaraGraphEdgeForRouting>(CapybaraGraphEdgeForRouting.class);
		
		List<GraphNode> nodes = nodeDao.findAll();
		
		for (GraphNode node : nodes) {
			graphNodeLookupTable.put(node.getNodeID(), node);
			mizzouGraph.addVertex(node.getNodeID());
		}
		
		List<GraphEdge> edges = edgeDao.findAll();
		
		for (GraphEdge edge: edges) {
			CapybaraGraphEdgeForRouting edgeForRouting = new CapybaraGraphEdgeForRouting(edge.getEdgeId());
			mizzouGraph.addEdge(edge.getFromNode().getNodeID(), edge.getToNode().getNodeID(), edgeForRouting);
			mizzouGraph.setEdgeWeight(edgeForRouting, edge.getDistance());
		}
	}
	
	public List<Point> ComputeRoute(Long startingBuildingId, Long endingBuildingId) throws JsonMappingException, JsonProcessingException {
		GraphPath<Long, CapybaraGraphEdgeForRouting> djikstraPath = DijkstraShortestPath.findPathBetween(mizzouGraph, startingBuildingId, endingBuildingId);
		List<CapybaraGraphEdgeForRouting> pathEdges = djikstraPath.getEdgeList();
		
		ArrayList<Point> pointsList = new ArrayList<Point>();
		
		for (CapybaraGraphEdgeForRouting capybaraEdge : pathEdges) {
			System.out.println(capybaraEdge.getEdgeId().toString());
			GraphEdge actualEdge = edgeDao.getById(capybaraEdge.getEdgeId());
			String pathShape = actualEdge.getPathshape();
			List<Point> points = OpenRouteServiceDao.MapToPointList(pathShape);
			pointsList.addAll(points);
		}
		
		return pointsList;
	}
	
}
