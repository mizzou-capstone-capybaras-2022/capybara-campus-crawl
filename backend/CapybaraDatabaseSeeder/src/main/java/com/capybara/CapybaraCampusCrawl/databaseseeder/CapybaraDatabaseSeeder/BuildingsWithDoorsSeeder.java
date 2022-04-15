package com.capybara.CapybaraCampusCrawl.databaseseeder.CapybaraDatabaseSeeder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;

import com.capybara.CapybaraCampusCrawl.databaseseeder.Models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReaderHeaderAware;


public class BuildingsWithDoorsSeeder {
	
	private Logger log;
	
	private String buildingDoorsPath;
	private String buildingDoorDistancesPath;
	private String shortenedBuildingNameToFullBuildingNamePath;
	
	
	private GraphNodeRepository nodeDao;
	private BuildingRepository buildingDao;
	private DoorRepository doorDao;
	private GraphEdgeRepository graphEdgeDao;
	
	private CSVReaderHeaderAware buildingDoorsReader;
	private CSVReaderHeaderAware buildingDoorDistancesReader;
	private CSVReaderHeaderAware shortenedBuildingNameToFullBuildingNameReader;
	
	public BuildingsWithDoorsSeeder(String buildingDoorsPath, String buildingDoorDistancesPath,
			String shortenedBuildingNameToFullBuildingNamePath, GraphNodeRepository nodeDao,
			BuildingRepository buildingDao, DoorRepository doorDao, GraphEdgeRepository graphEdgeDao, Logger log) {
		super();
		this.log = log;
		this.buildingDoorsPath = buildingDoorsPath;
		this.buildingDoorDistancesPath = buildingDoorDistancesPath;
		this.shortenedBuildingNameToFullBuildingNamePath = shortenedBuildingNameToFullBuildingNamePath;
		this.nodeDao = nodeDao;
		this.buildingDao = buildingDao;
		this.doorDao = doorDao;
		this.graphEdgeDao = graphEdgeDao;
	}
	
	public void Seed() {
		
		List<Map<String, String>> buildingsDoorsToSeedRawData = new ArrayList<>();
		List<Map<String, String>> buildingDoorDistancesToSeedRawData = new ArrayList<>();
		List<Map<String, String>> buildingShortNameToLongNameRawData = new ArrayList<>();
		
		try {
			CreateReaders();
			
			buildingsDoorsToSeedRawData = DatabaseSeederHelper.readFileHashMap(buildingDoorsReader);
			buildingDoorDistancesToSeedRawData = DatabaseSeederHelper.readFileHashMap(buildingDoorDistancesReader);
			buildingShortNameToLongNameRawData = DatabaseSeederHelper.readFileHashMap(shortenedBuildingNameToFullBuildingNameReader);
			
			CloseReaders();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HashMap<String, String> shortNameToFullNameDictionary = getShortNameToLongNameLookup(buildingShortNameToLongNameRawData);
	
		HashMap<String, HashMap<String, Pair<Double, Double>>> buildingDoors = getBuildingDoorsForSeeding(buildingsDoorsToSeedRawData, shortNameToFullNameDictionary);
				
		HashMap<String, Building> savedBuildingsLookup = saveBuildings(buildingDoors);
		
		HashMap<String, HashMap<String, Door>> savedDoorsLookup = saveBuildingDoors(buildingDoors, savedBuildingsLookup);
	
		saveBuildingDoorEdges(buildingDoorDistancesToSeedRawData, savedDoorsLookup, shortNameToFullNameDictionary);
		
	}
	
	private void CreateReaders() throws FileNotFoundException, IOException {
		buildingDoorsReader = new CSVReaderHeaderAware(new BufferedReader(new FileReader(buildingDoorsPath)));
		buildingDoorDistancesReader = new CSVReaderHeaderAware(new BufferedReader(new FileReader(buildingDoorDistancesPath)));
		shortenedBuildingNameToFullBuildingNameReader = new CSVReaderHeaderAware(new BufferedReader(new FileReader(shortenedBuildingNameToFullBuildingNamePath)));
	}
	
	private void CloseReaders() throws IOException {
		buildingDoorsReader.close();
		buildingDoorDistancesReader.close();
		shortenedBuildingNameToFullBuildingNameReader.close();
	}
	
	private void LogBuildingDoorLocations(HashMap<String, HashMap<String, Pair<Double, Double>>> buildingDoors) {
		for (Map.Entry<String, HashMap<String, Pair<Double, Double>>> entry : buildingDoors.entrySet()) {
			log.info(entry.getKey() + " doors: ");
			for (Map.Entry<String, Pair<Double, Double>> doorEntry : entry.getValue().entrySet()) {
				log.info("Door " + doorEntry.getKey() + ", " + "Latitude: " + doorEntry.getValue().getLeft() + ", " + "Longitude: " + doorEntry.getValue().getRight());
			}
		}
	}
	
	private String getShortBuildingNameFromDoorName(String buildingDoorName) {
		return buildingDoorName.substring(0, buildingDoorName.length() - 1);
	}
	
	private String getDoorCodeFromDoorName(String buildingDoorName) {
		return buildingDoorName.substring(buildingDoorName.length() - 1, buildingDoorName.length());
	}
	
	private String getFullBuildingNameForShortBuildingName(String shortBuildingName, HashMap<String, String> shortNameToFullNameDictionary) {
		return shortNameToFullNameDictionary.get(shortBuildingName);
	}
	
	private HashMap<String, String> getShortNameToLongNameLookup(List<Map<String, String>> buildingShortNameToLongName){
		HashMap<String, String> shortNameToFullNameDictionary = new HashMap<>();
		
		for (Map<String, String> shortNameToLongName : buildingShortNameToLongName) {
			shortNameToFullNameDictionary.put(shortNameToLongName.get("Name Shortened"), shortNameToLongName.get("Full Name"));
		}
		
		return shortNameToFullNameDictionary;
	}
	
	private HashMap<String, HashMap<String, Pair<Double, Double>>> getBuildingDoorsForSeeding(List<Map<String, String>> buildingsDoorsToSeed, HashMap<String, String> shortNameToFullNameDictionary){
		HashMap<String, HashMap<String, Pair<Double, Double>>> buildingDoors = new HashMap<> ();
		
		//{ building-name: {door-name: door-location(lat, lon) }}
		
		//For each building-door
		//1. Get the full building name
		//2. Insert the full building name into dictionary if not in there
		//3. Insert the door information into the building's dictionary
	
		for (Map<String, String> buildingDoor : buildingsDoorsToSeed) {
			
			String buildingDoorName = buildingDoor.get("Building Name");
			Double longitude = Double.parseDouble(buildingDoor.get("Longitude"));
			Double latitude = Double.parseDouble(buildingDoor.get("Latitude"));
			
			String shortBuildingName = getShortBuildingNameFromDoorName(buildingDoorName);
			String fullBuildingName = getFullBuildingNameForShortBuildingName(shortBuildingName, shortNameToFullNameDictionary); 
			String doorCode = getDoorCodeFromDoorName(buildingDoorName);
			
			if (!buildingDoors.containsKey(fullBuildingName)) {
				buildingDoors.put(fullBuildingName, new HashMap<>());
			}
			
			if (!buildingDoors.get(fullBuildingName).containsKey(doorCode)) {
				Pair<Double, Double> locationDetails = Pair.of(latitude, longitude);
				buildingDoors.get(fullBuildingName).put(doorCode, locationDetails);
			}
		}
		
		return buildingDoors;
	}
	
	private HashMap<String, Building> saveBuildings(HashMap<String, HashMap<String, Pair<Double, Double>>> buildingDoors){
		HashMap<String, Building> savedBuildingsLookup = new HashMap<>();
		
		for (Map.Entry<String, HashMap<String, Pair<Double, Double>>> entry : buildingDoors.entrySet()) {
			String buildingName = entry.getKey();
			Building newBuilding = new Building(buildingName, "{}", null);
			
			Building savedBuilding = buildingDao.save(newBuilding);
			savedBuildingsLookup.put(buildingName, savedBuilding);
		}
		
		return savedBuildingsLookup;
	}
	
	private HashMap<String, HashMap<String, Door>> saveBuildingDoors(HashMap<String, HashMap<String, Pair<Double, Double>>> buildingDoors, HashMap<String, Building> savedBuildingsLookup){
		HashMap<String, HashMap<String, Door>> savedDoorsLookup = new HashMap<>();
		
		for (Map.Entry<String, HashMap<String, Pair<Double, Double>>> entry: buildingDoors.entrySet()) {
			String buildingName = entry.getKey();
		
			savedDoorsLookup.put(buildingName, new HashMap<>());
			
			for (Map.Entry<String, Pair<Double, Double>> doorEntry : entry.getValue().entrySet()) {
				Double latitude = doorEntry.getValue().getLeft();
				Double longitude = doorEntry.getValue().getRight();
				String doorDescription = "Door " + doorEntry.getKey() + " for " + buildingName;
				
				GraphNode savedGraphNode = nodeDao.save(new GraphNode(latitude, longitude, doorDescription));
				
				Building doorBuilding = savedBuildingsLookup.get(buildingName);
				
				Door newDoorToSave = new Door(savedGraphNode, doorBuilding);
				Door savedDoor = doorDao.save(newDoorToSave);
				
				savedDoorsLookup.get(buildingName).put(doorEntry.getKey(), savedDoor);
			}
		}
		
		return savedDoorsLookup;
	}
	
	private void saveBuildingDoorEdges(List<Map<String, String>> buildingDoorDistancesToSeedRawData, HashMap<String, HashMap<String, Door>> savedDoorsLookup, HashMap<String, String> shortNameToFullNameDictionary) {
		for (Map<String, String> buildingDoorEdge : buildingDoorDistancesToSeedRawData) {
			String door1 = buildingDoorEdge.get("Door1");
			String door2 = buildingDoorEdge.get("Door2");
			
			String fullBuildingName1 = getFullBuildingNameForShortBuildingName(getShortBuildingNameFromDoorName(door1), shortNameToFullNameDictionary); 
			String fullBuildingName2 = getFullBuildingNameForShortBuildingName(getShortBuildingNameFromDoorName(door2), shortNameToFullNameDictionary); 
			
			String doorCode1 = getDoorCodeFromDoorName(door1);
			String doorCode2 = getDoorCodeFromDoorName(door2);
			
			Double doorDistance = Double.parseDouble(buildingDoorEdge.get("Distance"));
			
			if ((savedDoorsLookup.containsKey(fullBuildingName1) && savedDoorsLookup.get(fullBuildingName1).containsKey(doorCode1)) &&
				(savedDoorsLookup.containsKey(fullBuildingName2) && savedDoorsLookup.get(fullBuildingName2).containsKey(doorCode2))){
				Door door1Entity = savedDoorsLookup.get(fullBuildingName1).get(doorCode1);
				GraphNode door1Node = door1Entity.getNode();
				
				Door door2Entity = savedDoorsLookup.get(fullBuildingName2).get(doorCode2);
				GraphNode door2Node = door2Entity.getNode();
				
				ObjectMapper mapper = new ObjectMapper();
				
				ArrayList<double[]> coordList = new ArrayList<>();
				double[] fromPoint =  {door1Node.getLongitude(),door2Node.getLatitude()};
				double[] toPoint =  {door2Node.getLongitude(),door2Node.getLatitude()};
				
				coordList.add(fromPoint);
				coordList.add(toPoint);
				
				try {
					
					String coordsListString = mapper.writeValueAsString(coordList);
					
					
					GraphEdge doorEdgeToSave = new GraphEdge(door1Node, door2Node, "insideWalking", "insideWalking", doorDistance,coordsListString, true);
					
					graphEdgeDao.save(doorEdgeToSave);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
