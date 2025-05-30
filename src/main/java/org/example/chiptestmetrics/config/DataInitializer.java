package org.example.chiptestmetrics.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.chiptestmetrics.model.TestMetric;
import org.example.chiptestmetrics.repository.TestMetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class DataInitializer {

    @Autowired
    private TestMetricRepository repository;

    @Bean
    CommandLineRunner initData() {
        return args -> {
            // 檢查資料庫是否已有數據，若有則不進行初始化
            if (repository.count() > 0) {
                System.out.println("Database already contains data. Skipping initialization.");
                return;
            }

            try {
                // 讀取 test-data.json 檔案
                ObjectMapper mapper = new ObjectMapper();
                InputStream inputStream = new ClassPathResource("test-data.json").getInputStream();
                TestMetric[] metrics = mapper.readValue(inputStream, TestMetric[].class);

                // 將數據存入資料庫
                for (TestMetric metric : metrics) {
                    repository.save(metric);
                }
                System.out.println("Test data initialized with " + metrics.length + " entries.");
            } catch (Exception e) {
                System.err.println("Failed to initialize test data: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}
