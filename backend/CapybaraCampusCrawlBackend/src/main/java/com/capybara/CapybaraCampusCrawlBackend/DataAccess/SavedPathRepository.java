package com.capybara.CapybaraCampusCrawlBackend.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capybara.CapybaraCampusCrawlBackend.Models.SavedPath;

public interface SavedPathRepository extends JpaRepository<SavedPath, Long>{
	
	SavedPath findById(long id);
	
}
