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

package br.com.tecsinapse.glimpse.client.http;

import br.com.tecsinapse.glimpse.client.Connector;
import br.com.tecsinapse.glimpse.client.ConnectorException;
import br.com.tecsinapse.glimpse.protocol.PollResultItem;

import java.util.List;
import java.util.Map;

public class BackwardsCompatibilityHttpConnector implements Connector {

	private String url;

	private String username;

	private String password;

	private Connector delegate;

	public BackwardsCompatibilityHttpConnector(String url, String username,
			String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	private Connector getDelegate() throws ConnectorException {
		if (delegate == null) {
			NewHttpConnector newHttpConnector = new NewHttpConnector(url,
					username, password);
			if (newHttpConnector.isServerCompatible()) {
				delegate = newHttpConnector;
			} else {
				delegate = new HttpConnector(url, username, password);
			}
		}
		return delegate;
	}

	@Override
	public String start(String script, Map<String, String> params) throws ConnectorException {
		return getDelegate().start(script, params);
	}

	@Override
	public boolean isOpen(String id) throws ConnectorException {
		return getDelegate().isOpen(id);
	}

	@Override
	public List<PollResultItem> poll(String id) throws ConnectorException {
		return getDelegate().poll(id);
	}

	@Override
	public void cancel(String id) throws ConnectorException {
		getDelegate().cancel(id);
	}

	@Override
	public String createRepl() throws ConnectorException {
		return getDelegate().createRepl();
	}

	@Override
	public String eval(String replId, String script) throws ConnectorException {
		return getDelegate().eval(replId, script);
	}

	@Override
	public void closeRepl(String replId) throws ConnectorException {
		getDelegate().closeRepl(replId);
	}

}
