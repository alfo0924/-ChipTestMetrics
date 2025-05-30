package org.example.chiptestmetrics.service;

import org.example.chiptestmetrics.model.TestMetric;
import org.example.chiptestmetrics.repository.TestMetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestMetricService {
    @Autowired
    private TestMetricRepository repository;

    public TestMetric saveMetric(TestMetric metric) {
        return repository.save(metric);
    }

    public List<TestMetric> getAllMetrics() {
        return repository.findAll();
    }

    public TestMetric getMetricById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
