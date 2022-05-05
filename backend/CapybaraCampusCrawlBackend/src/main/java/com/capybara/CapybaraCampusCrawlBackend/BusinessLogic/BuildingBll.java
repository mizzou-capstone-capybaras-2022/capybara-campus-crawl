package com.capybara.CapybaraCampusCrawlBackend.BusinessLogic;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capybara.CapybaraCampusCrawlBackend.DataAccess.BuildingRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.DoorRepository;
import com.capybara.CapybaraCampusCrawlBackend.Models.Building;
import com.capybara.CapybaraCampusCrawlBackend.Models.Door;
import com.capybara.CapybaraCampusCrawlBackend.Models.GraphNode;

@Component
public class BuildingBll {

	private BuildingRepository buildingDao;

    private DoorRepository doorDao;
	
	@Autowired
	public BuildingBll(BuildingRepository buildingDao, DoorRepository doorDao) {
		this.buildingDao = buildingDao;
		this.doorDao = doorDao;
	}
		
	public List<Building> getSanitizedBuildings(){
    	List<Building> rawBuildings = buildingDao.findAll();
    	
    	List<Building> modifiedBuildings = rawBuildings.stream()
    			.map(building -> repairedBuildingWithLocation(building))
				.collect(Collectors.toList());
    	
    	return modifiedBuildings;
    }
	
	public Building repairedBuildingWithLocation(Building building) {
    	if (building.getGraphNode() != null) {
    		return building;
    	}
    	
    	Collection<Door> validDoors = doorDao.findAllDoorsForBuilding(building.getBuildingId());
		Door door = validDoors.iterator().next();
		
		Building repairedBuilding = building;
		repairedBuilding.setGraphNode(door.getNode());
		
		return repairedBuilding;
    }
	

	public GraphNode fetchBuildingGraphNode(BigDecimal buildingId) {		
		return fetchBuildingGraphNode(buildingId.longValue());
	}
	
	public GraphNode fetchBuildingGraphNode(long buildingId) {
		Building building = buildingDao.findById(buildingId);
		return repairedBuildingWithLocation(building).getGraphNode();	
	}
	
}
