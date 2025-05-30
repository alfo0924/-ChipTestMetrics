package org.example.chiptestmetrics.controller;

import org.example.chiptestmetrics.model.TestMetric;
import org.example.chiptestmetrics.service.TestMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
public class TestMetricController {
    @Autowired
    private TestMetricService service;

    @PostMapping
    public TestMetric addMetric(@RequestBody TestMetric metric) {
        return service.saveMetric(metric);
    }

    @GetMapping
    public List<TestMetric> getAllMetrics() {
        return service.getAllMetrics();
    }

    @GetMapping("/{id}")
    public TestMetric getMetricById(@PathVariable Long id) {
        return service.getMetricById(id);
    }
}
