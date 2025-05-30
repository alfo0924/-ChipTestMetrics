package org.example.chiptestmetrics.repository;

import org.example.chiptestmetrics.model.TestMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestMetricRepository extends JpaRepository<TestMetric, Long> {
}
