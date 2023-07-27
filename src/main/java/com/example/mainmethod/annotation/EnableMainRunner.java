package com.example.mainmethod.annotation;

import com.example.mainmethod.config.MainAnnotationRunnerModuleConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Import(MainAnnotationRunnerModuleConfiguration.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableMainRunner {
}
