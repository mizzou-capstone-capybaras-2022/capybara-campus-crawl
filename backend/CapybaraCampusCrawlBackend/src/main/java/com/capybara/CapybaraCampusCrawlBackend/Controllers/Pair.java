package com.capybara.CapybaraCampusCrawlBackend.Controllers;



import java.util.ArrayList;

import com.capybara.CapybaraCampusCrawlBackend.Models.Point;

public class Pair{
		public ArrayList<Point> coords;
		Double distance;
		
		public Pair(ArrayList<Point> coords,Double distance) {
			this.coords = coords;
			this.distance = distance;
		}
	}