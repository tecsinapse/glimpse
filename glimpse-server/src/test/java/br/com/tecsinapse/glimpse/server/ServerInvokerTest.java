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

import br.com.tecsinapse.glimpse.protocol.Operation;
import br.com.tecsinapse.glimpse.protocol.PollOp;
import br.com.tecsinapse.glimpse.protocol.PollResult;
import br.com.tecsinapse.glimpse.protocol.PollResultItem;
import br.com.tecsinapse.glimpse.protocol.Result;
import br.com.tecsinapse.glimpse.protocol.StartOp;
import br.com.tecsinapse.glimpse.protocol.StartResult;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ServerInvokerTest {

	private ServerInvoker serverInvoker;
	
	private String script = "myScript";
	
	private String jobId = "myJobId";
	
	private List<PollResultItem> polls = Arrays.asList(Mockito.mock(PollResultItem.class));
	
	public ServerInvokerTest() {
		Server server = Mockito.mock(Server.class);
		serverInvoker = new ServerInvoker(server);
		Mockito.when(server.start(script, Collections.<String, String>emptyMap())).thenReturn(jobId);
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
