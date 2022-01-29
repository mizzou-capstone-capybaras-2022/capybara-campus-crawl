package com.capybara.CapybaraCampusCrawlBackend.Models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GraphNodeRepository extends JpaRepository<GraphNode, Long>{
	
	List<GraphNode> findByLongitudeAndLatitude(Double longitude, Double latitude);
	
	GraphNode findById(long id);
	
}
