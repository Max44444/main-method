package com.example.mainmethod.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainAnnotationRunnerModuleConfiguration {

	@Bean
	public MainAnnotationListener mainAnnotationListener() {
		return new MainAnnotationListener();
	}

	@Bean
	public BeanDefinitionAppenderBeanPostProcessor beanDefinitionAppenderBeanPostProcessor() {
		return new BeanDefinitionAppenderBeanPostProcessor();
	}

}
