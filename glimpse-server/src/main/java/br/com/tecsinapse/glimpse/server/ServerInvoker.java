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

import java.util.Map;

import com.google.common.collect.Maps;

import br.com.tecsinapse.glimpse.protocol.CancelOp;
import br.com.tecsinapse.glimpse.protocol.Operation;
import br.com.tecsinapse.glimpse.protocol.PollOp;
import br.com.tecsinapse.glimpse.protocol.Result;
import br.com.tecsinapse.glimpse.protocol.StartOp;

public class ServerInvoker {

	private Map<Class<? extends Operation>, OperationInvoker<? extends Operation, ? extends Result>> invokersByClass = Maps.newHashMap();

	public ServerInvoker(Server server) {
		invokersByClass.put(StartOp.class, new StartOpInvoker(server));
		invokersByClass.put(PollOp.class, new PollOpInvoker(server));
		invokersByClass.put(CancelOp.class, new CancelOpInvoker(server));
	}

	public Result invoke(Operation operation) {
		OperationInvoker invoker = invokersByClass.get(operation.getClass());
		return invoker.invoke(operation);
	}
	
}
