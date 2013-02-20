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

package br.com.tecsinapse.glimpse.server;

import java.util.Arrays;
import java.util.List;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import br.com.tecsinapse.glimpse.server.protocol.Operation;
import br.com.tecsinapse.glimpse.server.protocol.PollOp;
import br.com.tecsinapse.glimpse.server.protocol.PollResult;
import br.com.tecsinapse.glimpse.server.protocol.Result;
import br.com.tecsinapse.glimpse.server.protocol.StartOp;
import br.com.tecsinapse.glimpse.server.protocol.StartResult;

public class ServerInvokerTest {

	private ServerInvoker serverInvoker;
	
	private String script = "myScript";
	
	private String jobId = "myJobId";
	
	private List<ServerPoll> polls = Arrays.asList(Mockito.mock(ServerPoll.class));
	
	public ServerInvokerTest() {
		Server server = Mockito.mock(Server.class);
		serverInvoker = new ServerInvoker(server);
		Mockito.when(server.start(script)).thenReturn(jobId);
		Mockito.when(server.poll(jobId)).thenReturn(polls);
	}
	
	@DataProvider(name="operation-and-results")
	public Object[][] getOperationAndResults() {
		return new Object[][] {
			{ new StartOp(script), new StartResult(jobId) },
			{ new PollOp(jobId), new PollResult(polls) }
		};
	}
	
	@Test(dataProvider="operation-and-results")
	public void invoke(Operation operation, Result expectedResult) {
		Result result = serverInvoker.invoke(operation);
		Assert.assertEquals(result, expectedResult);
	}
	
}
