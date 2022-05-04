package com.capybara.CapybaraCampusCrawlBackend.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import com.capybara.CapybaraCampusCrawlBackend.DataAccess.OpenRouteServiceDao;
import com.capybara.CapybaraCampusCrawlBackend.Models.Location;
import com.capybara.CapybaraCampusCrawlBackend.Models.PitstopConstraint;
import com.capybara.CapybaraCampusCrawlBackend.Models.Point;
import com.capybara.CapybaraCampusCrawlBackend.Models.RouteRequest;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import javax.validation.Valid;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-18T22:02:47.023914Z[Etc/UTC]")
@Controller
@RequestMapping("${openapi.openAPIDefinition.base-path:}")
public class RoutesApiController implements RoutesApi {

	@Autowired
	OpenRouteServiceDao routeDao;
	
    private final NativeWebRequest request;

    @Autowired
    public RoutesApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    public ResponseEntity<List<Point>> getRoute(
            @Parameter(name = "RouteRequest", description = "Get the Route with a generic route request", required = true, schema = @Schema(description = "")) @Valid @RequestBody RouteRequest routeRequest
    	    ) {

                System.out.println("Prefer Indoors: " + routeRequest.getConstraints().getPreferIndoors());
                System.out.println("Stop by food: " + routeRequest.getConstraints().getStopForFood());
                System.out.println("Avoid Crouds:" + routeRequest.getConstraints().getAvoidCrowds());
                
                List<PitstopConstraint> pitstops = routeRequest.getConstraints().getPitstops();
                
                if (pitstops.size() > 0) {
                    Location location = pitstops.get(0).getLocation();
                    
                    System.out.println("Building ID: " + location.getBuildingId());
                    System.out.println("Building Lat: " + location.getLatitude());
                    System.out.println("Building Long: " + location.getLongitude());
                }
                
                if (routeRequest.getConstraints().getTimeConstraint().isPresent()) {
                    System.out.println("Max Time: "+ routeRequest.getConstraints().getTimeConstraint().get().getMaxTime());
                }else {
                	System.out.println("Max Time: "+ "null");
                }
                

    	return new ResponseEntity<List<Point>>(new ArrayList<Point>(), HttpStatus.OK);
    }
    
}
