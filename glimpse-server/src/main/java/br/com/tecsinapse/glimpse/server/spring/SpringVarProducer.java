/*
 * Copyright 2012 Tecsinapse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
