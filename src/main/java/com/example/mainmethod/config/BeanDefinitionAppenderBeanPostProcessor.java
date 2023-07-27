package com.example.mainmethod.config;

import com.example.mainmethod.annotation.Main;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.util.Objects.isNull;

@Component
public class BeanDefinitionAppenderBeanPostProcessor implements BeanPostProcessor {

	private ConfigurableListableBeanFactory beanFactory;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (hasMethodAnnotatedWithMain(bean)) {
			var beanDefinition = beanFactory.getBeanDefinition(beanName);
			if (isNull(beanDefinition.getBeanClassName())) {
				beanDefinition.setBeanClassName(bean.getClass().getCanonicalName());
			}
		}

		return bean;
	}

	private boolean hasMethodAnnotatedWithMain(Object bean) {
		return Arrays.stream(bean.getClass().getMethods())
				.anyMatch(method -> method.isAnnotationPresent(Main.class));
	}

	@Autowired
	public void setBeanFactory(ConfigurableListableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
}
