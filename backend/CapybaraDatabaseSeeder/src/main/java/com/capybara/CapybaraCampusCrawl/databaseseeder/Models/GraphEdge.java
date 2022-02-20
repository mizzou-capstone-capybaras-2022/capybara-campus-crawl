package com.capybara.CapybaraCampusCrawl.databaseseeder.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "\"GraphEdge\"")
public class GraphEdge {
	
	@Id
	@Column(name="\"EdgeID\"")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long edgeId;
	
	@OneToOne
	@JoinColumn(name="\"Node1ID\"", nullable=false)
	private GraphNode fromNode;
	
	@OneToOne
	@JoinColumn(name="\"Node2ID\"", nullable=false)
	private GraphNode toNode;
	
	@Column(name="fromtoaction")
	private String fromToAction;
	
	@Column(name="tofromaction")
	private String toFromAction;
	
	private Double distance;
	
	private String pathshape;
	
	private boolean bidirectional;
	
	public GraphEdge() {
		super();
	}

	public GraphEdge(GraphNode fromNode, GraphNode toNode, String fromToAction, String toFromAction, Double distance,
			String pathshape, boolean bidirectional) {
		super();
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.fromToAction = fromToAction;
		this.toFromAction = toFromAction;
		this.distance = distance;
		this.pathshape = pathshape;
		this.bidirectional = bidirectional;
	}
	
	public GraphNode getFromNode() {
		return fromNode;
	}

	public void setFromNode(GraphNode fromNode) {
		this.fromNode = fromNode;
	}

	public GraphNode getToNode() {
		return toNode;
	}

	public void setToNode(GraphNode toNode) {
		this.toNode = toNode;
	}

	public String getFromToAction() {
		return fromToAction;
	}

	public void setFromToAction(String fromToAction) {
		this.fromToAction = fromToAction;
	}

	public String getToFromAction() {
		return toFromAction;
	}

	public void setToFromAction(String toFromAction) {
		this.toFromAction = toFromAction;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getPathshape() {
		return pathshape;
	}

	public void setPathshape(String pathshape) {
		this.pathshape = pathshape;
	}

	public boolean isBidirectional() {
		return bidirectional;
	}

	public void setBidirectional(boolean bidirectional) {
		this.bidirectional = bidirectional;
	}

	public Long getEdgeId() {
		return edgeId;
	}

	@Override
	public String toString() {
		return "GraphEdge [edgeId=" + edgeId + ", fromNode=" + fromNode.getNodeID() + ", toNode=" + toNode.getNodeID() + ", fromToAction="
				+ fromToAction + ", toFromAction=" + toFromAction + ", distance=" + distance + ", pathshape="
				+ pathshape + ", bidirectional=" + bidirectional + "]";
	}
	
}
