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

public class DefaultRepl implements Repl {

	private String replId;
	private Connector connector;

	public DefaultRepl(Connector connector) {
		this.connector = connector;
		this.replId = connector.createRepl();
	}

	public String eval(String script) {
		return connector.eval(replId, script);
	}

	public void close() {
		connector.closeRepl(replId);
	}

}
