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
import br.com.tecsinapse.glimpse.protocol.ClosePollResultItem;
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
	
	private Result invoke(Operation startOp) {
		String result = invoker.invoke("/", Marshaller.marshall(startOp));
		return (Result) Parser.parse(result);
	}
	
	@Override
	public String start(String script) {
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
	public List<PollResultItem> poll(String id) {
		PollResult result = (PollResult) invoke(new PollOp(id));
		List<PollResultItem> items = result.getItems();
		for (PollResultItem pollResultItem : items) {
			if (pollResultItem instanceof ClosePollResultItem) {
				ids.remove(id);
			}
		}
		return items;
	}

	@Override
	public void cancel(String id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public String createRepl() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public String eval(String replId, String script) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void closeRepl(String replId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
