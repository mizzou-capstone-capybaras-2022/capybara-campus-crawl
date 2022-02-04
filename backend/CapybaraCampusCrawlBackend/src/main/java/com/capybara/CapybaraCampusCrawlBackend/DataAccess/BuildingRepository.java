package com.capybara.CapybaraCampusCrawlBackend.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capybara.CapybaraCampusCrawlBackend.Models.Building;

public interface BuildingRepository extends JpaRepository<Building, Long>{
	
	Building findById(long id);
	
}
