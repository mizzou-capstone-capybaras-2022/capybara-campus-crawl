package com.capybara.CapybaraCampusCrawlBackend.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import com.capybara.CapybaraCampusCrawlBackend.Models.Building;
import com.capybara.CapybaraCampusCrawlBackend.Models.BuildingRouteRequest;
import com.capybara.CapybaraCampusCrawlBackend.Models.Point;

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
public class BuildingRouteApiController implements BuildingRouteApi {

    private final NativeWebRequest request;

    @Autowired
    public BuildingRouteApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }
    
    public ResponseEntity<List<Point>> getSimpleRouteBetweenBuildings(
            @Parameter(name = "BuildingRouteRequest", description = "Get the Route with a building specific route request", required = true, schema = @Schema(description = "")) @Valid @RequestBody BuildingRouteRequest buildingRouteRequest
        ) {
            
    		return new ResponseEntity<List<Point>>(new ArrayList<Point>(), HttpStatus.OK);

        }

}
