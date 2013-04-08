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

import br.com.tecsinapse.glimpse.protocol.EvalOp;
import br.com.tecsinapse.glimpse.protocol.EvalResult;

public class EvalOpInvokerTest {

	@Test
	public void invoke() {
		String id = "myId";
		String expression = "myExpression";
		String result = "myResult";
		Server server = Mockito.mock(Server.class);
		Mockito.when(server.eval(id, expression)).thenReturn(result);
		EvalOpInvoker invoker = new EvalOpInvoker(server);
		EvalOp evalOp = new EvalOp(id, expression);
		EvalResult evalResult = new EvalResult(result);
		Assert.assertEquals(invoker.invoke(evalOp), evalResult);
	}

}
