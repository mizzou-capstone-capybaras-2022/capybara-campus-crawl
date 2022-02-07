package com.capybara.CapybaraCampusCrawlBackend.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capybara.CapybaraCampusCrawlBackend.Models.Door;
import com.capybara.CapybaraCampusCrawlBackend.Models.GraphEdge;

public interface GraphEdgeRepository extends JpaRepository<GraphEdge, Long>{
	
	GraphEdge findById(long id);
	
}

