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

package br.com.tecsinapse.glimpse.client;

import java.util.List;

public class DefaultScriptRunner implements ScriptRunner {

	private Connector connector;

	public DefaultScriptRunner(Connector connector) {
		this.connector = connector;
	}

	public void run(final String script, final Monitor monitor) {
		String id = connector.start(script);

		while (connector.isOpen(id)) {
			if (monitor.isCanceled()) {
				connector.cancel(id);
			}
			List<ClientPoll> polls = connector.poll(id);
			if (polls.isEmpty()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new IllegalStateException(e);
				}
			} else {
				for (ClientPoll clientPoll : polls) {
					clientPoll.apply(monitor);
				}
			}
		}
	}

}
