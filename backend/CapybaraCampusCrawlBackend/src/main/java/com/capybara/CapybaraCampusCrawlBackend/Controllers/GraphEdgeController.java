package com.capybara.CapybaraCampusCrawlBackend.Controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capybara.CapybaraCampusCrawlBackend.DataAccess.BuildingRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.GraphEdgeRepository;
import com.capybara.CapybaraCampusCrawlBackend.Models.*;

@RestController
@RequestMapping("/graph-edges")
public class GraphEdgeController {
	@Autowired
	private GraphEdgeRepository edgeDao;
	
	@GetMapping("/")
	public Collection<GraphEdge> index() {
		return edgeDao.findAll();
	}
}