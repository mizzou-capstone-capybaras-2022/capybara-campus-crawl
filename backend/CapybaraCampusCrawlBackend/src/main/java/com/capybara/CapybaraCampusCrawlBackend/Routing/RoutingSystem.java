package com.capybara.CapybaraCampusCrawlBackend.Routing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capybara.CapybaraCampusCrawlBackend.CapybaraCampusCrawlBackendApplication;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.GraphEdgeRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.GraphNodeRepository;
import com.capybara.CapybaraCampusCrawlBackend.Models.GraphEdge;
import com.capybara.CapybaraCampusCrawlBackend.Models.GraphNode;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.jgrapht.*;
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
	
	private Graph<Long, DefaultEdge> mizzouGraph;
	
	private HashMap<Long, GraphNode> graphNodeLookupTable = new HashMap<>();
	
	@Inject
	public RoutingSystem(GraphEdgeRepository edgeDao, GraphNodeRepository nodeDao) {
		this.edgeDao = edgeDao;
		this.nodeDao = nodeDao;
		
		mizzouGraph = new DefaultUndirectedWeightedGraph<Long, DefaultEdge>(DefaultEdge.class);
		
		List<GraphNode> nodes = nodeDao.findAll();
		
		for (GraphNode node : nodes) {
			graphNodeLookupTable.put(node.getNodeID(), node);
			mizzouGraph.addVertex(node.getNodeID());
		}
		
		List<GraphEdge> edges = edgeDao.findAll();
		
		for (GraphEdge edge: edges) {
			DefaultEdge addedEdge = mizzouGraph.addEdge(edge.getFromNode().getNodeID(), edge.getToNode().getNodeID());
			mizzouGraph.setEdgeWeight(addedEdge, edge.getDistance());
		}
	}
	
}
