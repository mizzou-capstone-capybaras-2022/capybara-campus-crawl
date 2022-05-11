package com.capybara.CapybaraCampusCrawlBackend.Routing;

import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class CapybaraGraph extends SimpleDirectedWeightedGraph<Long, CapybaraGraphEdge> {

    public CapybaraGraph() {
        super(CapybaraGraphEdge.class);
    }
}
