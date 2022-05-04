package com.capybara.CapybaraCampusCrawlBackend.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import com.capybara.CapybaraCampusCrawlBackend.DataAccess.GraphNodeRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.PlaceRepository;
import com.capybara.CapybaraCampusCrawlBackend.Models.GraphNode;
import com.capybara.CapybaraCampusCrawlBackend.Models.Place;

@Controller
@RequestMapping("${openapi.openAPIDefinition.base-path:}")
public class PlacesController implements PlacesApi {

		private PlaceRepository placesDao;
	
		private final NativeWebRequest request;
		
	 	@Autowired
	    public PlacesController(NativeWebRequest request, PlaceRepository placesDao) {
	        this.placesDao = placesDao;
	        this.request = request;
	    }

	    @Override
	    public Optional<NativeWebRequest> getRequest() {
	        return Optional.ofNullable(request);
	    }
	    
	    public ResponseEntity<List<Place>> getPlaces(){
	    	return new ResponseEntity<>(placesDao.findAll(), HttpStatus.OK);
	    }

	
}
