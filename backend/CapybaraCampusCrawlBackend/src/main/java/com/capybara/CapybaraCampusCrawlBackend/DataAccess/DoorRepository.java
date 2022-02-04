package com.capybara.CapybaraCampusCrawlBackend.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capybara.CapybaraCampusCrawlBackend.Models.Door;

public interface DoorRepository extends JpaRepository<Door, Long>{
	
	Door findById(long id);
	
}
