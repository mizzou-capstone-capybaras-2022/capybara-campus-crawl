package com.capybara.CapybaraCampusCrawl.databaseseeder.CapybaraDatabaseSeeder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.capybara.CapybaraCampusCrawl.databaseseeder.Models.Building;
import com.capybara.CapybaraCampusCrawl.databaseseeder.Models.BuildingRepository;
import com.capybara.CapybaraCampusCrawl.databaseseeder.Models.GraphNode;
import com.capybara.CapybaraCampusCrawl.databaseseeder.Models.GraphNodeRepository;
import com.opencsv.CSVReaderHeaderAware;

public class BuildingsOnlyPointsSeeder {
	private Logger log;
	
	private String buildingOnlyPointsFileName;
	
	private GraphNodeRepository nodeDao;
	private BuildingRepository buildingDao;
	
	public BuildingsOnlyPointsSeeder(String buildingOnlyPointsFileName, GraphNodeRepository nodeDao, BuildingRepository buildingDao, Logger logger) {
		this.buildingOnlyPointsFileName = buildingOnlyPointsFileName;
		this.nodeDao = nodeDao;
		this.buildingDao = buildingDao;
		this.log = logger;
	}
	
	public void SeedBuildings() {
		CSVReaderHeaderAware reader;
		
		List<Map<String, String>> buildingsToSeed = new ArrayList<Map<String, String>>();
		
		try {
			BufferedReader streamReader = new BufferedReader(new FileReader(buildingOnlyPointsFileName));
			reader = new CSVReaderHeaderAware(streamReader);
			log.info("preparing to read all files");
			buildingsToSeed = DatabaseSeederHelper.readFileHashMap(reader);
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for (Map<String, String> buildingRow : buildingsToSeed) {
			log.info(buildingRow.toString());
			
			Double nodeLatitude = Double.parseDouble(buildingRow.get("Latitude"));
			Double nodeLongitude = Double.parseDouble(buildingRow.get("Longitude"));
			String nodeDescription = buildingRow.get("Description");
			String buildingName = buildingRow.get("Building Name");
			
			GraphNode graphNode = new GraphNode(nodeLatitude, nodeLongitude, nodeDescription);
			graphNode = nodeDao.save(graphNode);
			
			if (graphNode.getNodeID() != null) {
				Building building = new Building(buildingName, "{}", graphNode);
				buildingDao.save(building);
			}
		}
	}
}
