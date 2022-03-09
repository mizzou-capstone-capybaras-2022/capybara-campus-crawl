package com.capybara.CapybaraCampusCrawlBackend.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import com.capybara.CapybaraCampusCrawlBackend.DataAccess.GraphNodeRepository;
import com.capybara.CapybaraCampusCrawlBackend.Models.GraphEdge;
import com.capybara.CapybaraCampusCrawlBackend.Models.GraphNode;

import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-09T11:18:48.083513-06:00[America/Chicago]")
@Controller
@RequestMapping("${openapi.openAPIDefinition.base-path:}")
public class GraphNodesApiController implements GraphNodesApi {

	@Autowired
	private GraphNodeRepository graphNodeDao;
	
    private final NativeWebRequest request;

    @Autowired
    public GraphNodesApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }
    
    public ResponseEntity<List<GraphNode>> getNodes(){
    	return new ResponseEntity<List<GraphNode>>(graphNodeDao.findAll(), HttpStatus.OK);   
    }

}
