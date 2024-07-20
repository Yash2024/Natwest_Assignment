package com.yash.ReportGen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.unit.DataSize;

import jakarta.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableScheduling
public class ReportGenApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportGenApplication.class, args);
	}

	@Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("2000MB"));
        factory.setMaxRequestSize(DataSize.parse("2000MB"));
        return factory.createMultipartConfig();
    }
}
