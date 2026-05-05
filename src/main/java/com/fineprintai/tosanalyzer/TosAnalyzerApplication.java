package com.fineprintai.tosanalyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TosAnalyzerApplication {

    private static final Logger logger = LoggerFactory.getLogger(TosAnalyzerApplication.class);

    public static void main(String[] args) {
        logger.info("Starting TosAnalyzerApplication");
        SpringApplication.run(TosAnalyzerApplication.class, args);
        logger.info("TosAnalyzerApplication started successfully");
    }

}