package com.capybara.CapybaraCampusCrawl.databaseseeder.Models;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface BuildingRepository extends JpaRepository<Building, Long>{
	
	Building findById(long id);

}
