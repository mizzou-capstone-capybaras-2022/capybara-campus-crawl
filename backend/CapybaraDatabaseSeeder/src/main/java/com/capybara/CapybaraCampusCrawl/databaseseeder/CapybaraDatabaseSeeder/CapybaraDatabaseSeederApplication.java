package com.capybara.CapybaraCampusCrawl.databaseseeder.CapybaraDatabaseSeeder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.capybara.CapybaraCampusCrawl.databaseseeder.Models.*;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;

@SpringBootApplication
@EntityScan("com.capybara.CapybaraCampusCrawl.databaseseeder.Models")
@EnableJpaRepositories("com.capybara.CapybaraCampusCrawl.databaseseeder.Models")
public class CapybaraDatabaseSeederApplication {
	
	private static final String SHARED_FILE_PATH = "src/main/resources";
	
	private static final Logger log = LoggerFactory.getLogger(CapybaraDatabaseSeederApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(CapybaraDatabaseSeederApplication.class, args);
	}

	@Bean
	public PhysicalNamingStrategy physical() {
	    return new PhysicalNamingStrategyStandardImpl();
	}

	@Bean
	public ImplicitNamingStrategy implicit() {
	    return new ImplicitNamingStrategyLegacyJpaImpl();
	}

	@Bean
	public CommandLineRunner runner(GraphNodeRepository nodeDao, BuildingRepository buildingDao, DoorRepository doorDao, GraphEdgeRepository graphEdgeDao) {
		log.debug("preparing to read all files");
		return (args) -> {
			String buildingsWithoutDoorsFileName = SHARED_FILE_PATH + "/building-points.csv";
			
			BuildingsOnlyPointsSeeder buildingSeeder = new BuildingsOnlyPointsSeeder(buildingsWithoutDoorsFileName, nodeDao, buildingDao, log);
			
			log.info("Seeding buildings without any doors!: ");
			buildingSeeder.Seed();
			
			String buildingDoorsPath = SHARED_FILE_PATH + "/building-doors.csv";
			String buildingDoorDistancesPath = SHARED_FILE_PATH + "/building-inside-distances.csv";
			String shortenedBuildingNameToFullBuildingNamePath = SHARED_FILE_PATH + "/shortened-building-name-to-full-building-name.csv";
			
			BuildingsWithDoorsSeeder buildingWithDoorsSeeder = new BuildingsWithDoorsSeeder(buildingDoorsPath, buildingDoorDistancesPath,shortenedBuildingNameToFullBuildingNamePath,nodeDao,buildingDao,doorDao,graphEdgeDao,log);

			log.info("Seeding buildings with doors!: ");
			buildingWithDoorsSeeder.Seed();
				
		};
				
	}

}
