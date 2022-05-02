package com.capybara.CapybaraCampusCrawlBackend.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import com.capybara.CapybaraCampusCrawlBackend.DataAccess.DoorRepository;
import com.capybara.CapybaraCampusCrawlBackend.Models.Building;
import com.capybara.CapybaraCampusCrawlBackend.Models.Door;

import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;

@Controller
@RequestMapping("${openapi.openAPIDefinition.base-path:}")
public class DoorsApiController implements DoorsApi {
    private DoorRepository doorDao;

    private final NativeWebRequest request;

    @Autowired
    public DoorsApiController(NativeWebRequest request, DoorRepository doorDao) {
        this.doorDao = doorDao;
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    public ResponseEntity<List<Door>> getDoors() {
    	return new ResponseEntity<>(this.doorDao.findAll(), HttpStatus.OK);
    }
}
