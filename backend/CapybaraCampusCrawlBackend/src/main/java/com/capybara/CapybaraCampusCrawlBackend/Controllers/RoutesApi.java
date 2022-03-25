/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (5.4.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.capybara.CapybaraCampusCrawlBackend.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.capybara.CapybaraCampusCrawlBackend.Models.Point;
import com.capybara.CapybaraCampusCrawlBackend.Models.RouteRequest;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-23T06:46:32.599746Z[Etc/UTC]")
@Validated
public interface RoutesApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * PUT /routes/ : Get a route
     *
     * @param routeRequest Get the Route with a generic route request (required)
     * @return OK (status code 200)
     *         or Route not possible (status code 404)
     *         or Internal Server Error (status code 500)
     */
    @Operation(
        operationId = "getRoute",
        summary = "Get a route",
        tags = { "Route Controller" },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  Point.class))),
            @ApiResponse(responseCode = "404", description = "Route not possible"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/routes/",
		produces = {"application/json", "text/xml"},
        consumes = { "application/json" }
    )
    default ResponseEntity<List<Point>> getRoute(
        @Parameter(name = "RouteRequest", description = "Get the Route with a generic route request", required = true, schema = @Schema(description = "")) @Valid @RequestBody RouteRequest routeRequest
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("*/*"))) {
                    String exampleString = "{ \"latitude\" : 0.8008281904610115, \"longitude\" : 6.027456183070403 }";
                    ApiUtil.setExampleResponse(request, "*/*", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
