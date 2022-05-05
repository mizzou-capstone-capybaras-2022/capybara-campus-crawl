package com.capybara.CapybaraCampusCrawlBackend.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

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
    
    private RoutingSystem routingDao;
    
    private BuildingRepository buildingDao;
    
    private DoorRepository doorDao;

    @Autowired
    public RoutesApiController(NativeWebRequest request, RoutingSystem routeDao, BuildingRepository buildingDao, DoorRepository doorDao) {
        this.request = request;
        this.doorDao = doorDao;
        this.buildingDao = buildingDao;
        this.routingDao = routeDao;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    private static boolean hasNoConstraints(RouteRequestConstraints constraints) {
    	return constraints.getPreferIndoors() == false 
                && constraints.getStopForFood() == false 
                && constraints.getAvoidCrowds() == false
                && constraints.getPitstops().size() == 0
                && constraints.getTimeConstraint().isEmpty();
    }
    
    public ResponseEntity<List<Point>> getRoute(
            @Parameter(name = "RouteRequest", description = "Get the Route with a generic route request", required = true, schema = @Schema(description = "")) @Valid @RequestBody RouteRequest routeRequest
    	    ) {

    			RouteRequestConstraints constraints = routeRequest.getConstraints();
    			
                System.out.println("Prefer Indoors: " + constraints.getPreferIndoors());
                System.out.println("Stop by food: " + constraints.getStopForFood());
                System.out.println("Avoid Crouds:" + constraints.getAvoidCrowds());
                
                List<PitstopConstraint> pitstops = constraints.getPitstops();
                
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
                
                if (hasNoConstraints(constraints)) {
                	BigDecimal buildingIdFrom = routeRequest.getFromLocation().getBuildingId();
                	BigDecimal buildingIdTo = routeRequest.getToLocation().getBuildingId();
                	
                	Building buildingA = buildingDao.findById(buildingIdFrom.longValue());
            		Building buildingB = buildingDao.findById(buildingIdTo.longValue());
           
            		GraphNode graphNodeA = BuildingsApiController.repairedBuildingWithLocation(buildingA, doorDao).getGraphNode();
            		GraphNode graphNodeB = BuildingsApiController.repairedBuildingWithLocation(buildingB, doorDao).getGraphNode();
            		
            		List<Point> points = new ArrayList<Point>();
            		
            		try {
        				points = routingDao.ComputeRoute(graphNodeA.getNodeID(), graphNodeB.getNodeID());
        			} catch (Exception e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}

                	return new ResponseEntity<List<Point>>(points, HttpStatus.OK);
                }
                
                
            	return new ResponseEntity<List<Point>>(new ArrayList<Point>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
