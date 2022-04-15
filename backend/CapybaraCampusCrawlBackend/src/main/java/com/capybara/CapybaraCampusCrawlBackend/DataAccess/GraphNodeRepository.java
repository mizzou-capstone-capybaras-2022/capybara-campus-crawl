package com.capybara.CapybaraCampusCrawlBackend.DataAccess;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.capybara.CapybaraCampusCrawlBackend.Models.GraphNode;

@Component
public interface GraphNodeRepository extends JpaRepository<GraphNode, Long>{
	
	List<GraphNode> findByLongitudeAndLatitude(Double longitude, Double latitude);
	
	GraphNode findById(long id);
	
}
