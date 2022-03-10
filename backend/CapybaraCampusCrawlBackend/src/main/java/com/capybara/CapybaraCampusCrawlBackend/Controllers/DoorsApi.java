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

import com.capybara.CapybaraCampusCrawlBackend.Models.Door;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-09T11:18:48.083513-06:00[America/Chicago]")
@Validated
@Tag(name = "Door Controller", description = "the doors API")
public interface DoorsApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /doors/
     *
     * @return OK (status code 200)
     */
    @Operation(
        operationId = "getDoors",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  Door.class)))
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/doors/",
        produces = { "*/*" }
    )
    default ResponseEntity<List<Door>> getDoors(
        
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("*/*"))) {
                    String exampleString = "{ \"node\" : { \"latitude\" : 0.8008281904610115, \"description\" : \"description\", \"nodeID\" : 1, \"longitude\" : 6.027456183070403 }, \"doorId\" : 0, \"building\" : { \"geojson\" : \"geojson\", \"name\" : \"name\", \"graphNode\" : { \"latitude\" : 0.8008281904610115, \"description\" : \"description\", \"nodeID\" : 1, \"longitude\" : 6.027456183070403 }, \"nodeID\" : 1, \"buildingId\" : 6 } }";
                    ApiUtil.setExampleResponse(request, "*/*", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}