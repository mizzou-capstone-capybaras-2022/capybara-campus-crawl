package com.capybara.CapybaraCampusCrawlBackend.Controllers;

import com.capybara.CapybaraCampusCrawlBackend.Models.PiMetric;
import com.capybara.CapybaraCampusCrawlBackend.Models.Place;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.Generated;
import java.util.List;
import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-18T22:02:47.023914Z[Etc/UTC]")
@Validated
public interface RaspberryApi {
    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /buildings/
     *
     * @return OK (status code 200)
     */
    @Operation(
            operationId = "getMetrics",
            tags = { "Metrics Controller" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  PiMetric.class)))
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/metrics/",
            produces = {"application/json"}
    )
    default ResponseEntity<List<PiMetric>> getMetrics(

    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("*/*"))) {
                    String exampleString = "[{\n" +
                            "    \"metricId\": 1,\n" +
                            "    \"node\": {\n" +
                            "      \"latitude\": 38.94236171,\n" +
                            "      \"longitude\": -92.32665998,\n" +
                            "      \"description\": \"Door A for Student Center\",\n" +
                            "      \"nodeID\": 105\n" +
                            "    },\n" +
                            "    \"time\": \"2022-05-10 10:42:12\",\n" +
                            "    \"intensity\": 235\n" +
                            "  }]";
                    ApiUtil.setExampleResponse(request, "*/*", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }
}
