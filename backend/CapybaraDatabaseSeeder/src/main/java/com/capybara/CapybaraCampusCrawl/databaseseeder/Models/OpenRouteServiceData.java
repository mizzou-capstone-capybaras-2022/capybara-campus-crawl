package com.capybara.CapybaraCampusCrawl.databaseseeder.Models;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OpenRouteServiceData {

	private List<double[]> path;
	private Double distance;
	
	public OpenRouteServiceData(List<double[]> path, Double distance) {
		this.path = path;
		this.distance = distance;
	}
	
	public static OpenRouteServiceData parseFromGSON(String geoJson) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode featuresNode = mapper.readTree(geoJson).get("features").get(0);
		JsonNode distanceNode = featuresNode.get("properties").get("summary").get("distance");
		JsonNode coordinates = featuresNode.get("geometry").get("coordinates");
		
		Double routeDistance;
		
		if (distanceNode != null) {
			routeDistance = distanceNode.asDouble();
		}else {
			routeDistance = null;
		}
		
		List<double[]> routePathList = StreamSupport
				  .stream(coordinates.spliterator(), false)
				  .map(routePoint -> {
					  Double longitude = routePoint.get(0).asDouble();
					  Double latitude = routePoint.get(1).asDouble();
					  return new double[]{longitude, latitude};
				  })
				  .collect(Collectors.toList());
		
		return new OpenRouteServiceData(routePathList, routeDistance);
		
	}
	
	public Double getDistance() {
		return this.distance;
	}
	
	public String pathAsString() {
		String coordinateListString = "[]";
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			coordinateListString = mapper.writeValueAsString(this.path);
		}catch (Exception ex) {
			coordinateListString = "[]";
		}
		
		return coordinateListString;
	}
	
	public List<double[]> getPathPoints(){
		return this.path;
	}
	
	@Override
	public String toString() {
		String coordinateListString = pathAsString();
		return "Coordinates: " + coordinateListString + "\n" + "Distance: " + this.distance.toString();
	}
}
