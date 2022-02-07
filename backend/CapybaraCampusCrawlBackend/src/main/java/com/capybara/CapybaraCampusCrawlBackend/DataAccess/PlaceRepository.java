package com.capybara.CapybaraCampusCrawlBackend.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capybara.CapybaraCampusCrawlBackend.Models.Place;

public interface PlaceRepository extends JpaRepository<Place, Long>{
	
	Place findById(long id);
	
}