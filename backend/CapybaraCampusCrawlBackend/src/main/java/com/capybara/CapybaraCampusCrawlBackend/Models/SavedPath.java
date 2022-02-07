package com.capybara.CapybaraCampusCrawlBackend.Models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "\"SavedPath\"")
public class SavedPath {
	@Id
	@Column(name="\"SavedPathID\"")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long pathId;
	

	@OneToOne
	@JoinColumn(name="\"startingNodeID\"", nullable=false)
	private GraphNode startingNode;
	
	@OneToOne
	@JoinColumn(name="\"endingNodeID\"", nullable=false)
	private GraphNode endingNode;
	
	private double distance;
	
	@ManyToMany (fetch = FetchType.EAGER)
	@JoinTable(
		  name = "\"PathEdges\"", 
		  joinColumns = @JoinColumn(name = "\"SavedPathID\""), 
		  inverseJoinColumns = @JoinColumn(name = "\"PathEdgeID\""))
	Set<GraphEdge> pathEdges;

	public SavedPath(GraphNode startingNode, GraphNode endingNode, double distance, Set<GraphEdge> pathEdges) {
		super();
		this.startingNode = startingNode;
		this.endingNode = endingNode;
		this.distance = distance;
		this.pathEdges = pathEdges;
	}

	public SavedPath() {
		super();
	}
	
	public GraphNode getStartingNode() {
		return startingNode;
	}

	public void setStartingNode(GraphNode startingNode) {
		this.startingNode = startingNode;
	}

	public GraphNode getEndingNode() {
		return endingNode;
	}

	public void setEndingNode(GraphNode endingNode) {
		this.endingNode = endingNode;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Set<GraphEdge> getPathEdges() {
		return pathEdges;
	}

	public void setPathEdges(Set<GraphEdge> pathEdges) {
		this.pathEdges = pathEdges;
	}

	public Long getPathId() {
		return pathId;
	}

	@Override
	public String toString() {
		return "SavedPath [pathId=" + pathId + ", startingNode=" + startingNode.getNodeID() + ", endingNode=" + endingNode.getNodeID()
				+ ", distance=" + distance + ", pathEdges=" + pathEdges.toString() + "]";
	}
	
}
