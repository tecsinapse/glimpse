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

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import br.com.tecsinapse.glimpse.protocol.CreateReplOp;
import br.com.tecsinapse.glimpse.protocol.CreateReplResult;

public class CreateReplOpInvokerTest {
	
	@Test
	public void invoke() {
		CreateReplOp createReplOp = new CreateReplOp();
		Server server = Mockito.mock(Server.class);
		CreateReplOpInvoker invoker = new CreateReplOpInvoker(server);
		String id = "myId";
		Mockito.when(server.createRepl()).thenReturn(id);
		Assert.assertEquals(((CreateReplResult) invoker.invoke(createReplOp)).getReplId(), id);
	}
}
