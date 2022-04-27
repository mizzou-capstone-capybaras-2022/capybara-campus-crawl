package com.capybara.CapybaraCampusCrawlBackend.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import com.capybara.CapybaraCampusCrawlBackend.DataAccess.BuildingRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.DoorRepository;
import com.capybara.CapybaraCampusCrawlBackend.Models.Building;
import com.capybara.CapybaraCampusCrawlBackend.Models.Door;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.Generated;

@Controller
@RequestMapping("${openapi.openAPIDefinition.base-path:}")
public class BuildingsApiController implements BuildingsApi {

    private final NativeWebRequest request;

    @Autowired 
    private BuildingRepository buildingDao;
    
    @Autowired
    private DoorRepository doorDao;
    
    @Autowired
    public BuildingsApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }
    
    public ResponseEntity<List<Building>> getBuildings() {
    	List<Building> buildingToReturn = getSanitizedBuildings();
    	
    	return new ResponseEntity<List<Building>>(buildingToReturn, HttpStatus.OK);
    }
    
    private List<Building> getSanitizedBuildings(){
    	List<Building> rawBuildings = buildingDao.findAll();
    	
    	List<Building> modifiedBuildings = rawBuildings.stream()
				.filter(building -> building.getGraphNode() != null)
				.collect(Collectors.toList());
    	
    	List<Building> unmodifiedBuildings = rawBuildings.stream()
				.filter(building -> building.getGraphNode() == null)
				.collect(Collectors.toList());
    	
    	List<Building> fixedBuildings = addMissingLocationToBuildings(unmodifiedBuildings);
    	
    	modifiedBuildings.addAll(fixedBuildings);
    	return modifiedBuildings;
    }
    
    private List<Building> addMissingLocationToBuildings(List<Building> brokenBuildings) {
    	
    	List<Building> repairedBuildings = new ArrayList<Building>();
    	
    	for (Building repairedBuilding: brokenBuildings) {
    		Collection<Door> validDoors = doorDao.findAllDoorsForBuilding(repairedBuilding.getBuildingId());
    		Door door = validDoors.iterator().next();
    		
    		repairedBuilding.setGraphNode(door.getNode());
    		repairedBuildings.add(repairedBuilding);
    	}
    	
    	return repairedBuildings;
    }
}
