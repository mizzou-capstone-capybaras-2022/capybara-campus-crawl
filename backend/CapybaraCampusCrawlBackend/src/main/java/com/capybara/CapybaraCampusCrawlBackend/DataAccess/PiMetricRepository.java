package com.capybara.CapybaraCampusCrawlBackend.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capybara.CapybaraCampusCrawlBackend.Models.PiMetric;

public interface PiMetricRepository extends JpaRepository<PiMetric, Long>{
	
	PiMetric findById(long id);
	
}