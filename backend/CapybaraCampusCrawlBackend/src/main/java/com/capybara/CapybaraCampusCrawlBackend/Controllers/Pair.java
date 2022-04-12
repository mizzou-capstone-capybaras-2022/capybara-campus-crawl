package com.capybara.CapybaraCampusCrawlBackend.Controllers;



import java.util.ArrayList;

public class Pair{
		public ArrayList<double[]> coords;
		Double distance;
		
		public Pair(ArrayList<double[]> coords,Double distance) {
			this.coords = coords;
			this.distance = distance;
		}
	}