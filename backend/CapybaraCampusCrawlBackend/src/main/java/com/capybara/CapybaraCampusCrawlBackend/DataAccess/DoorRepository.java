package com.capybara.CapybaraCampusCrawlBackend.DataAccess;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capybara.CapybaraCampusCrawlBackend.Models.Door;

public interface DoorRepository extends JpaRepository<Door, Long>{
	
	Door findById(long id);
	
	@Query(
			value = "SELECT d.* FROM \"Door\" d WHERE d.\"BuildingID\" = ?1",
			nativeQuery = true
	)
	Collection<Door> findAllDoorsForBuilding(Long buildingId);
}
