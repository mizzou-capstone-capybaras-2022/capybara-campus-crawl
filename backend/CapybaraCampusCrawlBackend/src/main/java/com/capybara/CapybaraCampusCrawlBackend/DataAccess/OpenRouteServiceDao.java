package com.capybara.CapybaraCampusCrawlBackend.DataAccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenRouteServiceDao {

	@Value("${openrouteservice.api.key}")
	private String apiKey;
	
	@Autowired
	private RestTemplate restTemplate;
		
	public String GetDummyData() {
        String orsRunner = restTemplate.getForObject(
                "https://api.openrouteservice.org/v2/directions/foot-walking?api_key=" + apiKey + "&start=-92.32310414,38.94131544&end=-92.32290566,38.93858664", String.class);
        return orsRunner;
	}
	
}
