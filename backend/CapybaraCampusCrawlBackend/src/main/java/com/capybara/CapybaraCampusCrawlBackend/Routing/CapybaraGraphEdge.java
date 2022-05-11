package com.capybara.CapybaraCampusCrawlBackend.Routing;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.capybara.CapybaraCampusCrawlBackend.Models.Point;

public class CapybaraGraphEdge {
		public List<Point> coords;
		public Double distance;
		public boolean indoors;

		
		public CapybaraGraphEdge(List<Point> coords, boolean indoors, Double distance) {
			this.coords = coords;
			this.indoors = indoors;
			this.distance = distance;
		}

		public CapybaraGraphEdge getReverseEdge(){
			List<Point> reverseCoords = new ArrayList<>(this.coords);
			Collections.reverse(reverseCoords);

			return new CapybaraGraphEdge(reverseCoords, indoors, distance);
		}

	}