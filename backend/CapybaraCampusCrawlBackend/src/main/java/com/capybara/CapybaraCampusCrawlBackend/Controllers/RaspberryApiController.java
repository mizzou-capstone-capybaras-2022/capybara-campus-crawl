package com.capybara.CapybaraCampusCrawlBackend.Controllers;

import com.capybara.CapybaraCampusCrawlBackend.DataAccess.PiMetricRepository;
import com.capybara.CapybaraCampusCrawlBackend.DataAccess.PlaceRepository;
import com.capybara.CapybaraCampusCrawlBackend.Models.PiMetric;
import com.capybara.CapybaraCampusCrawlBackend.Models.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("${openapi.openAPIDefinition.base-path:}")
public class RaspberryApiController implements RaspberryApi {

    private PiMetricRepository metricDao;

    private final NativeWebRequest request;

    @Autowired
    public RaspberryApiController(NativeWebRequest request, PiMetricRepository metricDao) {
        this.request = request;
        this.metricDao = metricDao;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    public ResponseEntity<List<PiMetric>> getMetrics(){
        List<PiMetric> raspberryPiMetrics = this.metricDao.findAll();
        for (PiMetric metric: raspberryPiMetrics){
            System.out.println(metric.toString());
        }

        return new ResponseEntity<>(this.metricDao.findAll(), HttpStatus.OK);
    }

}
