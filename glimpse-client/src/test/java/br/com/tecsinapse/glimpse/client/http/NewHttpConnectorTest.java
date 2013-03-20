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

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import br.com.tecsinapse.glimpse.protocol.ClosePollResultItem;
import br.com.tecsinapse.glimpse.protocol.PollResultItem;
import br.com.tecsinapse.glimpse.protocol.StreamUpdatePollResultItem;
import br.com.tecsinapse.glimpse.server.Server;
import br.com.tecsinapse.glimpse.server.groovy.GroovyReplManager;
import br.com.tecsinapse.glimpse.server.groovy.GroovyScriptRunner;
import br.com.tecsinapse.glimpse.server.sunhttp.SunHttpConnector;

public class NewHttpConnectorTest {

	@Test
	public void startAndPoll() {
		Server server = new Server(new GroovyScriptRunner(), new GroovyReplManager());
		SunHttpConnector serverConnector = new SunHttpConnector(server, 8081);
		serverConnector.start();
		
		NewHttpConnector connector = new NewHttpConnector("http://localhost:8081", null, null);
		String id = connector.start("println 'hello'");
		List<PollResultItem> results = new ArrayList<PollResultItem>();
		while(connector.isOpen(id)) {
			results.addAll(connector.poll(id));
		}
		assertEquals(results.get(0), new StreamUpdatePollResultItem("hello"));
		assertEquals(results.get(1), new ClosePollResultItem());
	}
	
}
