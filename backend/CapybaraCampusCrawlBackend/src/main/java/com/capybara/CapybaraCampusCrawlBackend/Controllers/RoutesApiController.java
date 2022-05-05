package com.capybara.CapybaraCampusCrawlBackend.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import com.capybara.CapybaraCampusCrawlBackend.BusinessLogic.RoutingBll;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.BuildingRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.DoorRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.OpenRouteServiceDao;
import com.capybara.CapybaraCampusCrawlBackend.Models.Building;
import com.capybara.CapybaraCampusCrawlBackend.Models.GraphNode;
import com.capybara.CapybaraCampusCrawlBackend.Models.Location;
import com.capybara.CapybaraCampusCrawlBackend.Models.PitstopConstraint;
import com.capybara.CapybaraCampusCrawlBackend.Models.Point;
import com.capybara.CapybaraCampusCrawlBackend.Models.RouteRequest;
import com.capybara.CapybaraCampusCrawlBackend.Models.RouteRequestConstraints;
import com.capybara.CapybaraCampusCrawlBackend.Routing.RoutingSystem;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import javax.validation.Valid;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-18T22:02:47.023914Z[Etc/UTC]")
@Controller
@RequestMapping("${openapi.openAPIDefinition.base-path:}")
public class RoutesApiController implements RoutesApi {

		
    private final NativeWebRequest request;
    
    private RoutingBll routingBll;

    @Autowired
    public RoutesApiController(NativeWebRequest request, RoutingBll routingBll) {
        this.request = request;
        this.routingBll = routingBll;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    public ResponseEntity<List<Point>> getRoute
    (
	    @Parameter(
	    		name = "RouteRequest", 
	    		description = "Get the Route with a generic route request", 
	    		required = true, 
	    		schema = @Schema(description = "")) @Valid @RequestBody RouteRequest routeRequest
    ) 
    {
		List<Point> points = routingBll.fetchRoute(routeRequest);
    	return new ResponseEntity<List<Point>>(points, HttpStatus.OK);
    }
    
}
