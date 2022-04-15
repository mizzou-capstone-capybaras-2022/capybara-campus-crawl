package com.capybara.CapybaraCampusCrawl.databaseseeder.CapybaraDatabaseSeeder;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.capybara.CapybaraCampusCrawl.databaseseeder.CapybaraDatabaseSeeder.DataAccess.ORSDao;
import com.capybara.CapybaraCampusCrawl.databaseseeder.Models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReaderHeaderAware;


public class BuildingEdgesOutdoors {
	
	private Logger log;
		
	private GraphEdgeRepository graphEdgeDao;
	
	private GraphNodeRepository graphNodeDao;

	private JdbcTemplate jdbcTemplate;
	
	private ORSDao openRouteServiceDao;
	
	public BuildingEdgesOutdoors(GraphEdgeRepository graphEdgeDao, GraphNodeRepository graphNodeDao, JdbcTemplate jdbcTemplate, ORSDao openRouteServiceDao, Logger log) {
		super();
		this.log = log;
		this.openRouteServiceDao = openRouteServiceDao;
		this.jdbcTemplate = jdbcTemplate;
		this.graphEdgeDao = graphEdgeDao;
		this.graphNodeDao = graphNodeDao;
	}
	
	private List<Pair<GraphNode, GraphNode>> unseededNodePairs(){
		String sql = "SELECT \n"
				+ "	node1.\"NodeID\" as node1_id,\n"
				+ "	node2.\"NodeID\" as node2_id\n"
				+ "from \"GraphNode\" as node1, \"GraphNode\" as node2\n"
				+ "WHERE node1.\"NodeID\" <> node2.\"NodeID\"\n"
				+ "AND node1.\"NodeID\" < node2.\"NodeID\"\n"
				+ "AND concat(node1.\"NodeID\", ',' ,node2.\"NodeID\") NOT IN \n"
				+ "(\n"
				+ "	SELECT concat(edge.\"Node1ID\",',',edge.\"Node2ID\")\n"
				+ "	FROM \"GraphEdge\" as edge\n"
				+ ")";
					
		HashMap<Long, GraphNode> graphNodeLookup = new HashMap<>();
		Iterable<GraphNode> graphNodes = graphNodeDao.findAll();
		for (GraphNode graphNode : graphNodes) {
			graphNodeLookup.put(graphNode.getNodeID(), graphNode);
		}
	
		List<Pair<GraphNode,GraphNode>> unmappedNodePairs = jdbcTemplate.query(sql, new RowMapper<Pair<GraphNode,GraphNode>>() {
			public Pair<GraphNode, GraphNode> mapRow(ResultSet rs, int rowNum) throws SQLException {
				Long node1Id = rs.getLong("node1_id");
                Long node2Id = rs.getLong("node2_id");
                

    			GraphNode node1 = graphNodeLookup.get(node1Id);
    			GraphNode node2 = graphNodeLookup.get(node2Id);
                
                MutablePair<GraphNode,GraphNode> pair = new MutablePair<>(node1,node2);
                
                return pair;
            }
		});
		
		log.info("Nodes length: " + Integer.toString(unmappedNodePairs.size()));
		
		return unmappedNodePairs;
	}
	
	private GraphEdge buildEdgeDetails(Pair<GraphNode, GraphNode> nodePair) {
		Double fromLatitude = nodePair.getLeft().getLatitude();
		Double fromLongitude = nodePair.getLeft().getLongitude();
		Double toLatitude = nodePair.getRight().getLatitude();
		Double toLongitude = nodePair.getRight().getLongitude();
		
		GraphEdge builtGraphEdge = null;
		
		try {
			OpenRouteServiceData routeData = openRouteServiceDao.GetRouteBetweenPoints(fromLatitude, fromLongitude, toLatitude, toLongitude);
			builtGraphEdge = new GraphEdge();
			
			builtGraphEdge.setFromNode(nodePair.getLeft());
			builtGraphEdge.setToNode(nodePair.getRight());
			builtGraphEdge.setBidirectional(true);
			builtGraphEdge.setFromToAction("outsideWalking");
			builtGraphEdge.setToFromAction("outsideWalking");
			builtGraphEdge.setDistance(routeData.getDistance());
			builtGraphEdge.setPathshape(routeData.pathAsString());
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return builtGraphEdge;
	}
	
	public void Seed() {
		log.info("Building out Graph Edges");
		
		List<Pair<GraphNode, GraphNode>> graphNodePairs = unseededNodePairs();
		
		int savedEdges = 0;
		
		for (Pair<GraphNode, GraphNode> nodePair : graphNodePairs) {
			GraphEdge edgeToSave = buildEdgeDetails(nodePair);
			graphEdgeDao.save(edgeToSave);
			log.info("Seed " + nodePair.getLeft().getNodeID().toString() + "-" + nodePair.getRight().getNodeID().toString());
			savedEdges += 1;
		}
		
		log.info("Saved " + Integer.toString(savedEdges) + " graphEdges!");
	}
}
