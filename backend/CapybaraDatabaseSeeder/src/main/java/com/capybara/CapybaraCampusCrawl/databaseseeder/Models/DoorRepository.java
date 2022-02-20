package com.capybara.CapybaraCampusCrawl.databaseseeder.Models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DoorRepository extends JpaRepository<Door, Long>{
	
	Door findById(long id);
	
}
