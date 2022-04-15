package com.capybara.CapybaraCampusCrawl.databaseseeder.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "\"GraphNode\"")
public class GraphNode {
	
	@Id
	@Column(name="\"NodeID\"")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long NodeID;
	
	private Double latitude;
	private Double longitude;
	
	private String description;
	
	 @Override
	 public String toString() {
	   return String.format(
	       "GraphNode[id=%d, lat='%f', lon='%f', description='%s']",
	       NodeID, latitude, longitude, description);
	 }
	 
	public GraphNode() {
		super();
	}
	
	public GraphNode(long nodeId) {
		this.NodeID = nodeId;
	}
	 
	public GraphNode(Double latitude, Double longitude, String description) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.description = description;
	}

	public Long getNodeID() {
		return NodeID;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
