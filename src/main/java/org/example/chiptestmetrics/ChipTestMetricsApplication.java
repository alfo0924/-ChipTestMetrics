package org.example.chiptestmetrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChipTestMetricsApplication {
    private static final Logger logger = LoggerFactory.getLogger(ChipTestMetricsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ChipTestMetricsApplication.class, args);
        logger.info("ChipTestMetrics Application Started!");
    }
}
