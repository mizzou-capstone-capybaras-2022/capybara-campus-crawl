package com.capybara.CapybaraCampusCrawl.databaseseeder.CapybaraDatabaseSeeder.DataAccess;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.capybara.CapybaraCampusCrawl.databaseseeder.Models.OpenRouteServiceData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Component
public class ORSDao {

	@Autowired
	private RestTemplate restTemplate;
	
	public OpenRouteServiceData GetRouteBetweenPoints(Double startLat, Double startLong, Double endLat,	Double endLong) throws JsonMappingException, JsonProcessingException{

		String startString = startLong.toString() + "," + startLat.toString();
		String endString = endLong + "," + endLat.toString();
		
		String orsStringResponse = restTemplate.getForObject(
                "http://localhost:8080/ors/v2/directions/foot-walking?" + "start=" + startString + "&end=" + endString, String.class);
    
		return OpenRouteServiceData.parseFromGSON(orsStringResponse);
		
	}
	
}
