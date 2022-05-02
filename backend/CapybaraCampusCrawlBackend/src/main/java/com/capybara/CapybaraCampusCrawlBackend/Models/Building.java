package com.capybara.CapybaraCampusCrawlBackend.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
	
	public long getNodeID() {
		if (graphNode == null) {
			return -1;
		}else {
			return graphNode.getNodeID();
		}
	}
	
	@Override
	 public String toString() {
	   return String.format(
	       "Building[id=%d, name='%s', geojson='%s', node_id='%d']",
	       buildingId, name, geojson, getNodeID());
	 }
	 
	
	
}
