package com.capybara.CapybaraCampusCrawl.databaseseeder.Models;

import org.springframework.data.jpa.repository.JpaRepository;


public interface GraphEdgeRepository extends JpaRepository<GraphEdge, Long>{
	
	GraphEdge findById(long id);
	
}

