package com.capybara.CapybaraCampusCrawl.databaseseeder.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;


@Entity()
@Table(name = "\"Building\"")
public class Building {
	
	@Id
	@Column(name="\"BuildingID\"")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long buildingId;
	
	private String name;
	
	private String geojson;

	@OneToOne
	@JoinColumn(name="\"NodeID\"", nullable=true)
	private GraphNode graphNode;
	
	//TODO Many Doors belong to One Building
	
	public Building() {
		super();
	}
	

	public Building(String name, String geojson, GraphNode graphNode) {
		this.name = name;
		this.geojson = geojson;
		this.graphNode = graphNode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGeojson() {
		return geojson;
	}

	public void setGeojson(String geojson) {
		this.geojson = geojson;
	}

	public GraphNode getGraphNode() {
		return graphNode;
	}

	public void setGraphNode(GraphNode graphNode) {
		this.graphNode = graphNode;
	}

	public Long getBuildingId() {
		return buildingId;
	}
	
	public Long getNodeID() {
		if (graphNode == null) {
			return new Long(-1);
		}else {
			Long nodeId = graphNode.getNodeID();
			
			if (nodeId != null) {
				return nodeId;
			}else {
				return new Long(-1);
			}
		}
	}
	
	@Override
	 public String toString() {
	   return String.format(
	       "Building[id=%d, name='%s', geojson='%s', node_id='%s']",
	       buildingId, name, geojson, getNodeID().toString());
	 }
	 
	
	
}
