package com.capybara.CapybaraCampusCrawl.databaseseeder.Models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GraphNodeRepository extends CrudRepository<GraphNode, Long>{
	
	List<GraphNode> findByLongitudeAndLatitude(Double longitude, Double latitude);
	
	GraphNode findById(long id);
	
}
