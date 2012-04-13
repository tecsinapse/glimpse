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

package br.com.tecsinapse.glimpse.client;

import br.com.tecsinapse.glimpse.client.http.HttpConnector;


public class ScriptRunnerFactory {

	private ScriptRunnerFactory() {
	}
	
	/**
	 * Creates a ScriptRunner which can be used to run scripts against a given server.
	 * 
	 * @param url the url of the server
	 * @param username the username used to authenticate with the server
	 * @param password the password used to authenticate with the server
	 * @return a new ScriptRunner
	 */
	public static ScriptRunner create(String url, String username, String password) {
		return new DefaultScriptRunner(new HttpConnector(url, username, password));
	}
	
}
