package br.com.tecsinapse.glimpse.server.spring;

import groovy.lang.Script;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import br.com.tecsinapse.glimpse.server.groovy.VarProducer;

public class SpringVarProducer implements VarProducer, ApplicationContextAware {

	private ConfigurableApplicationContext applicationContext;
	
	public void fill(Script groovyScript) {
		ConfigurableBeanFactory beanFactory = applicationContext.getBeanFactory();
		groovyScript.setProperty("applicationContext", applicationContext);
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			BeanDefinition beanDef = beanFactory.getMergedBeanDefinition(beanName);
			if (beanDef.isSingleton()) {
				Object bean = applicationContext.getBean(beanName);
				groovyScript.setProperty(beanName, bean);
			}
		}
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = (ConfigurableApplicationContext) applicationContext;
	}

}
