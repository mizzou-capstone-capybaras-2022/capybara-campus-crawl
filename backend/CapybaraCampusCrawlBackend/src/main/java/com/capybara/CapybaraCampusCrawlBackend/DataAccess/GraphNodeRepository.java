package com.capybara.CapybaraCampusCrawlBackend.DataAccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capybara.CapybaraCampusCrawlBackend.Models.GraphNode;

public interface GraphNodeRepository extends JpaRepository<GraphNode, Long>{
	
	List<GraphNode> findByLongitudeAndLatitude(Double longitude, Double latitude);
	
	GraphNode findById(long id);
	
}
