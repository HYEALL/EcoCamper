package com.example.EcoCamper.config;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class ResourceConfiguration implements WebMvcConfigurer {
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/storage/**").addResourceLocations("file:///D:/seongeun/spring_boot/workspace/static/storage/");
	}
}
