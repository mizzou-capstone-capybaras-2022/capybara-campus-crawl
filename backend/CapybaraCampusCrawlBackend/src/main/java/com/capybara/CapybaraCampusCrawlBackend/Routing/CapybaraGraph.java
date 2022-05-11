package com.capybara.CapybaraCampusCrawlBackend.Routing;

import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class CapybaraGraph extends SimpleDirectedWeightedGraph<Long, CapybaraGraphEdge> {
    private boolean preferIndoorEdges = false;

    public CapybaraGraph() {
        super(CapybaraGraphEdge.class);
    }

    public void setPreferIndoors(boolean indoors){
        this.preferIndoorEdges = indoors;
    }

    @Override
    public double getEdgeWeight(CapybaraGraphEdge capybaraGraphEdge) {
        if (this.preferIndoorEdges && capybaraGraphEdge.indoors){
            return capybaraGraphEdge.distance * 6;
        }else {
            return capybaraGraphEdge.distance;
        }
    }
}
