package com.capybara.CapybaraCampusCrawlBackend.Routing;

import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class CapybaraGraph extends SimpleDirectedWeightedGraph<Long, CapybaraGraphEdge> {
    private boolean preferIndoorEdges = false;
    private boolean avoidCrowds = false;

    public CapybaraGraph() {
        super(CapybaraGraphEdge.class);
    }

    public void setPreferIndoors(boolean indoors){
        this.preferIndoorEdges = indoors;
    }

    public void setAvoidCrowds(boolean avoidCrowds) { this.avoidCrowds = avoidCrowds; };

    @Override
    public double getEdgeWeight(CapybaraGraphEdge capybaraGraphEdge) {
        Double distanceToMutate = capybaraGraphEdge.distance;

        if (this.avoidCrowds && capybaraGraphEdge.hasMetrics){
            double damperFactor = Math.pow(2, (1.0/30.0) * capybaraGraphEdge.metricIntensity);
            distanceToMutate = distanceToMutate * damperFactor;
        }

        if (this.preferIndoorEdges && capybaraGraphEdge.indoors){
            distanceToMutate = distanceToMutate / 6.0;
        }

        return distanceToMutate;
    }
}
