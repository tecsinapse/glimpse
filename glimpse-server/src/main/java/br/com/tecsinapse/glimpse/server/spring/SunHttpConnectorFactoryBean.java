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

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import br.com.tecsinapse.glimpse.server.Authenticator;
import br.com.tecsinapse.glimpse.server.ByPassAuthenticator;
import br.com.tecsinapse.glimpse.server.Server;
import br.com.tecsinapse.glimpse.server.groovy.GroovyReplManager;
import br.com.tecsinapse.glimpse.server.groovy.GroovyScriptRunner;
import br.com.tecsinapse.glimpse.server.sunhttp.SunHttpConnector;

public class SunHttpConnectorFactoryBean extends
		AbstractFactoryBean<SunHttpConnector> implements
		ApplicationContextAware {

	private int port;
	private Authenticator authenticator = new ByPassAuthenticator();
	private ApplicationContext applicationContext;
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	@Override
	protected SunHttpConnector createInstance() throws Exception {
		GroovyScriptRunner scriptRunner = new GroovyScriptRunner();
		SpringVarProducer springVarProducer = new SpringVarProducer();
		springVarProducer.setApplicationContext(applicationContext);
		scriptRunner.setVarProducer(springVarProducer);
		GroovyReplManager replManager = new GroovyReplManager();
		replManager.setVarProducer(springVarProducer);
		Server server = new Server(scriptRunner, replManager);
		SunHttpConnector httpConnector = new SunHttpConnector(server, port, authenticator);
		return httpConnector;
	}

	@Override
	public Class<?> getObjectType() {
		return SunHttpConnector.class;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public void start() throws Exception {
		getObject().start();
	}
	
	public void stop() throws Exception {
		getObject().stop();
	}

}
