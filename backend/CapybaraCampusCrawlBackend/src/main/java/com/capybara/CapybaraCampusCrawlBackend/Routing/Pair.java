package com.capybara.CapybaraCampusCrawlBackend.Routing;



import java.util.ArrayList;
import java.util.List;

import com.capybara.CapybaraCampusCrawlBackend.Models.Point;

public class Pair{
		public List<Point> coords;
		Double distance;
		
		public Pair(List<Point> coords, Double distance) {
			this.coords = coords;
			this.distance = distance;
		}
	}