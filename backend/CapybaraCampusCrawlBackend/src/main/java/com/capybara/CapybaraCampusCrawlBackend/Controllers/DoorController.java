package com.capybara.CapybaraCampusCrawlBackend.Controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capybara.CapybaraCampusCrawlBackend.DataAccess.BuildingRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.DoorRepository;
import com.capybara.CapybaraCampusCrawlBackend.Models.*;

@RestController
@RequestMapping("/doors")
public class DoorController {
	@Autowired
	private DoorRepository doorDao;
	
	@GetMapping("/")
	public Collection<Door> index() {
		return doorDao.findAll();
	}
}