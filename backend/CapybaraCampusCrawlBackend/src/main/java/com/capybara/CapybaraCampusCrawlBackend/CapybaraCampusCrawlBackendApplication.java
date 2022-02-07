package com.capybara.CapybaraCampusCrawlBackend;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capybara.CapybaraCampusCrawlBackend.DataAccess.BuildingRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.DoorRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.GraphNodeRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.PlaceRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.RoomRepository;
import com.capybara.CapybaraCampusCrawlBackend.Models.Building;
import com.capybara.CapybaraCampusCrawlBackend.Models.Door;
import com.capybara.CapybaraCampusCrawlBackend.Models.GraphNode;
import com.capybara.CapybaraCampusCrawlBackend.Models.Place;
import com.capybara.CapybaraCampusCrawlBackend.Models.Room;

import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.slf4j.Logger;

@SpringBootApplication()
public class CapybaraCampusCrawlBackendApplication {

	private static final Logger logger = LoggerFactory.getLogger(CapybaraCampusCrawlBackendApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(CapybaraCampusCrawlBackendApplication.class, args);
	}
	
	/*
	@Bean
	public CommandLineRunner demo(RoomRepository repository) {
		return (args) -> {
			logger.info("Fetching Rooms");
			
			for (Room room : repository.findAll()) {
				logger.info(room.toString());
			}
		};
		
	}
	*/
	
	@Bean
	public CommandLineRunner demo(PlaceRepository repository) {
		return (args) -> {
			logger.info("Finding Places");
			
			for (Place place : repository.findAll()) {
				logger.info(place.toString());
			}
			
		};
		
	}
	
	@Bean
	public PhysicalNamingStrategy physical() {
	    return new PhysicalNamingStrategyStandardImpl();
	}

	@Bean
	public ImplicitNamingStrategy implicit() {
	    return new ImplicitNamingStrategyLegacyJpaImpl();
	}

	
}
