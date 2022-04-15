package com.capybara.CapybaraCampusCrawlBackend.Models;

import org.jgrapht.graph.DefaultEdge;

public class CapybaraGraphEdgeForRouting extends DefaultEdge {
	private Long edgeId;
	
	public CapybaraGraphEdgeForRouting(Long edgeId) {
		this.edgeId = edgeId;
	}
	
	public Long getEdgeId() {
		return this.edgeId;
	}
	
	@Override
	public String toString() {
		return "(" + getSource() + " : " + getTarget() + " : " + edgeId.toString() + ")"; 
	}
}
