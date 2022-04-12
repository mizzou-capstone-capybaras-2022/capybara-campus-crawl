package com.capybara.CapybaraCampusCrawlBackend.DataAccess;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.capybara.CapybaraCampusCrawlBackend.Models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

@Component
public class OpenRouteServiceDao {

	@Value("${openrouteservice.api.key}")
	private String apiKey;
	
	@Autowired
	private RestTemplate restTemplate;
		
	public String GetDummyData() {
        String orsRunner = restTemplate.getForObject(
                "https://api.openrouteservice.org/v2/directions/foot-walking?api_key=" + apiKey + "&start=-92.32310414,38.94131544&end=-92.32290566,38.93858664", String.class);
        Point a = new Point().latitude(-92.32310414).longitude(38.94131544);
        Point b = new Point().latitude(-92.32290566).longitude(38.93858664);
        
        try {
			return GetRouteBetweenPoints(a,b).toString();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error occured"; 
	}
	
	public List<Point> GetRouteBetweenPoints(Point a, Point b) throws JsonMappingException, JsonProcessingException{
		String startString = a.getLongitude().toString() + "," + a.getLatitude().toString();
		String endString = b.getLongitude().toString() + "," + b.getLatitude().toString();
		String orsStringResponse = restTemplate.getForObject(
                "https://api.openrouteservice.org/v2/directions/foot-walking?api_key=" + apiKey + "&start=" + startString + "&end=" + endString, String.class);
        
		ObjectMapper mapper = new ObjectMapper();
	
		JsonNode node = mapper.readTree(orsStringResponse);
		JsonNode coordinates = node.get("features").get(0).get("geometry").get("coordinates");
		
		ArrayList<Point> points = new ArrayList<Point>();
		
		for (int i = 0; i < coordinates.size(); i++) {
			JsonNode coordinateNode = coordinates.get(i);
			Double latitude = coordinateNode.get(1).asDouble();
			Double longitude = coordinateNode.get(0).asDouble();
			
			points.add(new Point()
					.latitude(latitude)
					.longitude(longitude));
		}
		
		
		return points;
	}
	
}
