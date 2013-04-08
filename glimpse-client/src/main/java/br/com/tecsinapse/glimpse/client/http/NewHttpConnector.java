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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.tecsinapse.glimpse.client.Connector;
import br.com.tecsinapse.glimpse.client.ConnectorException;
import br.com.tecsinapse.glimpse.client.NotFoundException;
import br.com.tecsinapse.glimpse.protocol.CancelOp;
import br.com.tecsinapse.glimpse.protocol.CancelPollResultItem;
import br.com.tecsinapse.glimpse.protocol.ClosePollResultItem;
import br.com.tecsinapse.glimpse.protocol.CloseReplOp;
import br.com.tecsinapse.glimpse.protocol.CreateReplOp;
import br.com.tecsinapse.glimpse.protocol.CreateReplResult;
import br.com.tecsinapse.glimpse.protocol.EvalOp;
import br.com.tecsinapse.glimpse.protocol.EvalResult;
import br.com.tecsinapse.glimpse.protocol.Marshaller;
import br.com.tecsinapse.glimpse.protocol.Operation;
import br.com.tecsinapse.glimpse.protocol.Parser;
import br.com.tecsinapse.glimpse.protocol.PollOp;
import br.com.tecsinapse.glimpse.protocol.PollResult;
import br.com.tecsinapse.glimpse.protocol.PollResultItem;
import br.com.tecsinapse.glimpse.protocol.Result;
import br.com.tecsinapse.glimpse.protocol.StartOp;
import br.com.tecsinapse.glimpse.protocol.StartResult;

public class NewHttpConnector implements Connector {

	private HttpInvoker invoker;

	private Set<String> ids = new HashSet<String>();

	public NewHttpConnector(String url, String username, String password) {
		invoker = new HttpInvoker(url, username, password);
	}

	private Result invoke(Operation startOp) throws ConnectorException {
		String result = invoker.invoke("/", Marshaller.marshall(startOp));
		return (Result) Parser.parse(result);
	}

	@Override
	public String start(String script) throws ConnectorException {
		StartOp startOp = new StartOp(script);
		StartResult result = (StartResult) invoke(startOp);
		ids.add(result.getJobId());
		return result.getJobId();
	}

	@Override
	public boolean isOpen(String id) {
		return ids.contains(id);
	}

	@Override
	public List<PollResultItem> poll(String id) throws ConnectorException {
		PollResult result = (PollResult) invoke(new PollOp(id));
		List<PollResultItem> items = result.getItems();
		for (PollResultItem pollResultItem : items) {
			if (pollResultItem instanceof CancelPollResultItem
					|| pollResultItem instanceof ClosePollResultItem) {
				ids.remove(id);
			}
		}
		return items;
	}

	@Override
	public void cancel(String id) throws ConnectorException {
		invoke(new CancelOp(id));
	}

	@Override
	public String createRepl() throws ConnectorException {
		CreateReplResult result = (CreateReplResult) invoke(new CreateReplOp());
		return result.getReplId();
	}

	@Override
	public String eval(String replId, String script) throws ConnectorException {
		EvalResult result = (EvalResult) invoke(new EvalOp(replId, script));
		return result.getResult();
	}

	@Override
	public void closeRepl(String replId) throws ConnectorException {
		invoke(new CloseReplOp(replId));
	}

	public boolean isServerCompatible() throws ConnectorException {
		try {
			invoker.invoke("/version", "");
			return true;
		} catch (NotFoundException e) {
			return false;
		}
	}

}
