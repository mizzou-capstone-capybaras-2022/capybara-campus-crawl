package com.capybara.CapybaraCampusCrawlBackend;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capybara.CapybaraCampusCrawlBackend.Models.GraphNode;
import com.capybara.CapybaraCampusCrawlBackend.Models.GraphNodeRepository;

@RestController
public class GraphNodeController {
	
	private final GraphNodeRepository repository;
	
	GraphNodeController(GraphNodeRepository repository){
		this.repository = repository;
	}
	
	@GetMapping("/GetMapNodes")
	List<GraphNode> all(){
		List<GraphNode> graphNodes = repository.findAll();
		return graphNodes;
	}
}
