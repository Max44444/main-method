package com.example.mainmethod.config;

import com.example.mainmethod.annotation.Main;
import com.example.mainmethod.exception.MainRunnerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.util.Optional.ofNullable;

@Component
public class MainAnnotationListener {

	private ConfigurableListableBeanFactory beanFactory;

	@EventListener
	public void handleContextRefresh(ContextRefreshedEvent event) {
		var context = event.getApplicationContext();
		for (String name : context.getBeanDefinitionNames()) {
			var beanDefinition = beanFactory.getBeanDefinition(name);

			ofNullable(beanDefinition.getBeanClassName())
					.ifPresent(className -> runMainMethodOnBean(name, className, context));
		}

	}

	private void runMainMethodOnBean(String beanDefinitionName, String className, ApplicationContext context) {
		try {
			var beanClass = ClassUtils.resolveClassName(className, ClassLoader.getSystemClassLoader());

			for (Method method : beanClass.getMethods()) {
				if (method.isAnnotationPresent(Main.class)) {
					var bean = context.getBean(beanDefinitionName);
					method.setAccessible(true);
					method.invoke(bean);
				}
			}
		} catch (InvocationTargetException | IllegalAccessException e) {
			throw new MainRunnerException("Error during run method annotated with @Main: ", e);
		}
	}

	@Autowired
	public void setBeanFactory(ConfigurableListableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
}
