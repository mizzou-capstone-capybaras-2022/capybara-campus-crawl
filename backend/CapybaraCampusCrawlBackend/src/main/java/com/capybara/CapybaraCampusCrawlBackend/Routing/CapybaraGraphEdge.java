package com.capybara.CapybaraCampusCrawlBackend.Routing;



import java.util.List;

import com.capybara.CapybaraCampusCrawlBackend.Models.Point;

public class CapybaraGraphEdge {
		public List<Point> coords;
		Double distance;
		
		public CapybaraGraphEdge(List<Point> coords, Double distance) {
			this.coords = coords;
			this.distance = distance;
		}
	}