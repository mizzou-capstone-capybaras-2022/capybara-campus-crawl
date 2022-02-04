package com.capybara.CapybaraCampusCrawlBackend.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capybara.CapybaraCampusCrawlBackend.Models.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{
	
	Room findById(long id);
	
}
