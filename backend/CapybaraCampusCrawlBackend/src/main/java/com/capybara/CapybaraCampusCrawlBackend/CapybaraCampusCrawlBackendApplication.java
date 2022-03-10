package com.capybara.CapybaraCampusCrawlBackend;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.capybara.CapybaraCampusCrawlBackend.DataAccess.BuildingRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.DoorRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.GraphEdgeRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.GraphNodeRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.PiMetricRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.PlaceRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.RoomRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.SavedPathRepository;
import com.capybara.CapybaraCampusCrawlBackend.Models.Building;
import com.capybara.CapybaraCampusCrawlBackend.Models.Door;
import com.capybara.CapybaraCampusCrawlBackend.Models.GraphEdge;
import com.capybara.CapybaraCampusCrawlBackend.Models.GraphNode;
import com.capybara.CapybaraCampusCrawlBackend.Models.PiMetric;
import com.capybara.CapybaraCampusCrawlBackend.Models.Place;
import com.capybara.CapybaraCampusCrawlBackend.Models.Room;
import com.capybara.CapybaraCampusCrawlBackend.Models.SavedPath;

import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.slf4j.Logger;

@SpringBootApplication()
@EnableWebMvc
public class CapybaraCampusCrawlBackendApplication {

	private static final Logger logger = LoggerFactory.getLogger(CapybaraCampusCrawlBackendApplication.class);
	
	@Autowired
	private Environment env;
	
	public static void main(String[] args) {
		SpringApplication.run(CapybaraCampusCrawlBackendApplication.class, args);
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
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
        	String apiKey = env.getProperty("openrouteservice.api.key");
        	logger.info("Key:" + apiKey);
            String orsRunner = restTemplate.getForObject(
                    "https://api.openrouteservice.org/v2/directions/foot-walking?api_key=" + apiKey + "&start=-92.32310414,38.94131544&end=-92.32290566,38.93858664", String.class);
            logger.info(orsRunner);
        };
    }
	
}
