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

import br.com.tecsinapse.glimpse.protocol.CancelOp;
import br.com.tecsinapse.glimpse.protocol.CancelResult;

public class CancelOpInvokerTest {

	@Test
	public void invoke() {
		String jobId = "myId";
		CancelOp cancelOp = new CancelOp(jobId);
		Server server = Mockito.mock(Server.class);
		CancelOpInvoker invoker = new CancelOpInvoker(server);
		Assert.assertTrue(invoker.invoke(cancelOp) instanceof CancelResult);
		Mockito.verify(server, Mockito.times(1)).cancel(jobId);
	}
	
}
