package com.capybara.CapybaraCampusCrawl.databaseseeder.CapybaraDatabaseSeeder;

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
	
	private static List<GraphNode> parseCSVNodes(CSVReaderHeaderAware reader){
		List<GraphNode> newNodes = new ArrayList<GraphNode>();
		
		
		Map<String, String> csvValues;
		try {
			csvValues = reader.readMap();
			
			while (csvValues != null) {				
				Double latitude = Double.parseDouble(csvValues.get("Latitude"));
				Double longitude = Double.parseDouble(csvValues.get("Longitude"));
				String description = csvValues.get("Description");
				
				GraphNode node = new GraphNode(latitude, longitude, description);
				log.info(node.toString());
				
				newNodes.add(node);
				
				csvValues = reader.readMap();
			}
		} catch (CsvValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return newNodes;
	}
	
	@Bean
	public CommandLineRunner runner(GraphNodeRepository repository) {
		
		return (args) -> {
			// TODO Auto-generated method stub
			log.info("---- Start data seeding ----");
			
			InputStream stream = this.getClass().getResource("test.csv").openStream();
			
			Reader streamReader = new InputStreamReader(stream);
			
			CSVReaderHeaderAware reader = new CSVReaderHeaderAware(streamReader);

			List<GraphNode> parsedNodes = parseCSVNodes(reader);
			
			reader.close();
			
			Iterable<GraphNode> savedNodes = repository.saveAll(parsedNodes);
			
			log.info("Saved Nodes");
			for (GraphNode node : savedNodes) {
				log.info(node.toString());
			}
		};
				
	}

}
