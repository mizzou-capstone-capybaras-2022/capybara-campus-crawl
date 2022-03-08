package com.capybara.CapybaraCampusCrawlBackend.Controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capybara.CapybaraCampusCrawlBackend.DataAccess.*;
import com.capybara.CapybaraCampusCrawlBackend.Models.*;

@RestController
@RequestMapping("/graph-nodes")
public class GraphNodeController {
	@Autowired
	private GraphNodeRepository nodeDao;
	
	@GetMapping("/")
	public Collection<GraphNode> index() {
		return nodeDao.findAll();
	}
}