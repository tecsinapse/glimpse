package br.com.tecsinapse.glimpse.server.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import groovy.lang.Script;
import br.com.tecsinapse.glimpse.server.groovy.VarProducer;

public class SpringVarProducer implements VarProducer, ApplicationContextAware {

	private ApplicationContext applicationContext;
	
	public void fill(Script groovyScript) {
		groovyScript.setProperty("applicationContext", applicationContext);
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			Object bean = applicationContext.getBean(beanName);
			groovyScript.setProperty(beanName, bean);
		}
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

}
