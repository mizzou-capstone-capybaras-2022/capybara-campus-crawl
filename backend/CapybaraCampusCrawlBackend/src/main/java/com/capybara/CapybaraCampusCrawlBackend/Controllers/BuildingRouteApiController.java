package com.capybara.CapybaraCampusCrawlBackend.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import com.capybara.CapybaraCampusCrawlBackend.DataAccess.BuildingRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.OpenRouteServiceDao;
import com.capybara.CapybaraCampusCrawlBackend.Models.Building;
import com.capybara.CapybaraCampusCrawlBackend.Models.BuildingRouteRequest;
import com.capybara.CapybaraCampusCrawlBackend.Models.GraphNode;
import com.capybara.CapybaraCampusCrawlBackend.Models.Point;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import javax.validation.Valid;


@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-18T22:02:47.023914Z[Etc/UTC]")
@Controller
@RequestMapping("${openapi.openAPIDefinition.base-path:}")
public class BuildingRouteApiController implements BuildingRouteApi {

    private final NativeWebRequest request;

    @Autowired
	OpenRouteServiceDao routeDao;
    
    @Autowired
	BuildingRepository buildingDao;
    
    @Autowired
    public BuildingRouteApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }
    
    public ResponseEntity<List<Point>> getSimpleRouteBetweenBuildings(
            @Parameter(name = "BuildingRouteRequest", description = "Get the Route with a building specific route request", required = true, schema = @Schema(description = "")) @Valid @RequestBody BuildingRouteRequest buildingRouteRequest
        ) {
            
    		// building id->building obj->graph node object->point(lat, long)
    		
    		Building buildingOne = buildingRepository.findById(id1); // Where are the building IDs coming from?
    		Building buildingTwo = buildingRepository.findById(id2);
    		
    		GraphNode graphNodeOne = buildingOne.getGraphNode();
    		GraphNode graphNodeTwo = buildingTwo.getGraphNode();
    		
    		Point pointA = new Point().latitude(graphNodeOne.getLatitude()).longitude(graphNodeOne.getLongitude());
    		Point pointB = new Point().latitude(graphNodeTwo.getLatitude()).longitude(graphNodeTwo.getLongitude());
    		
    		String startString = pointA.getLatitude().toString() + "," + pointA.getLongitude().toString();
    		String endString = pointB.getLatitude().toString() + "," + pointB.getLongitude().toString();
    		String orsStringResponse = restTemplate.getForObject(
                    "https://api.openrouteservice.org/v2/directions/foot-walking?api_key=" + apiKey + "&start=" + startString + "&end=" + endString, String.class);
    		
    		ObjectMapper mapper = new ObjectMapper();
    		
    		JsonNode node = mapper.readTree(orsStringResponse);
    		JsonNode coordinates = node.get("features").get(0).get("geometry").get("coordinates");
    		
    		ArrayList<Point> points = new ArrayList<Point>();
    		
    		for (int i = 0; i < coordinates.size(); i++) {
    			JsonNode coordinateNode = coordinates.get(i);
    			Double latitude = coordinateNode.get(0).asDouble();
    			Double longitude = coordinateNode.get(1).asDouble();
    			
    			points.add(new Point().latitude(latitude).longitude(longitude));
    		}
    		
    		return new ResponseEntity<List<Point>>(new ArrayList<Point>(), HttpStatus.OK);

        }

}
