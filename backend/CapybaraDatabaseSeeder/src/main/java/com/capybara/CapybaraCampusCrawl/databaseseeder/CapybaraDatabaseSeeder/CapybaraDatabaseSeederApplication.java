package com.capybara.CapybaraCampusCrawl.databaseseeder.CapybaraDatabaseSeeder;

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
	
	@Bean
	public CommandLineRunner runner(GraphNodeRepository repository) {
		
		return (args) -> {
			// TODO Auto-generated method stub
			log.info("---- Start data seeding ----");
			
			log.info(new Long(repository.count()).toString());
			
			for (GraphNode graphnode: repository.findAll()) {
				log.info(graphnode.toString());
			}
		};
				
	}

}
