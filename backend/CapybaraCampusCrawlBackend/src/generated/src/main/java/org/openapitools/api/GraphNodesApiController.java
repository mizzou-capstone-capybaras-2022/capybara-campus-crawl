package org.openapitools.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.NativeWebRequest;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-09T11:18:48.083513-06:00[America/Chicago]")
@Controller
@RequestMapping("${openapi.openAPIDefinition.base-path:}")
public class GraphNodesApiController implements GraphNodesApi {

    private final NativeWebRequest request;

    @Autowired
    public GraphNodesApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

}
