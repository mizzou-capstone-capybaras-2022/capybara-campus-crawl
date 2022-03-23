package com.capybara.CapybaraCampusCrawlBackend.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import com.capybara.CapybaraCampusCrawlBackend.Models.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-18T22:02:47.023914Z[Etc/UTC]")
@Controller
@RequestMapping("${openapi.openAPIDefinition.base-path:}")
public class RoutesApiController implements RoutesApi {

    private final NativeWebRequest request;

    @Autowired
    public RoutesApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    public ResponseEntity<List<Point>> getRoute() {
    	return new ResponseEntity<List<Point>>(new ArrayList<Point>(), HttpStatus.OK);
    }

    
}
