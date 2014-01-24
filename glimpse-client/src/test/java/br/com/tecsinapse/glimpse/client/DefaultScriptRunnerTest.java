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

import br.com.tecsinapse.glimpse.protocol.ClosePollResultItem;
import br.com.tecsinapse.glimpse.protocol.PollResultItem;
import br.com.tecsinapse.glimpse.protocol.StreamUpdatePollResultItem;
import com.beust.jcommander.internal.Maps;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultScriptRunnerTest {

	@Test
	public void run() throws Exception {
		String jobId = "myJobId";
		String script = "myScript";
		
		String update = "update";
		StreamUpdatePollResultItem poll1 = new StreamUpdatePollResultItem(update);
		ClosePollResultItem poll2 = new ClosePollResultItem();

		Map<String, String> params = Maps.newHashMap();
		params.put("param1", "value1");

		Connector connector = mock(Connector.class);
		when(connector.start(script, params)).thenReturn(jobId);
		when(connector.isOpen(jobId)).thenReturn(true, true, false);
		when(connector.poll(jobId)).thenReturn(Arrays.asList((PollResultItem) poll1), Arrays.asList((PollResultItem) poll2));
		Monitor monitor = mock(Monitor.class);
		when(monitor.isCanceled()).thenReturn(false);
		
		DefaultScriptRunner runner = new DefaultScriptRunner(connector);
		runner.run(script, params, monitor);
		
		Mockito.verify(connector).start(script, params);
		Mockito.verify(connector, Mockito.times(3)).isOpen(jobId);
		Mockito.verify(connector, Mockito.times(2)).poll(jobId);
		
		Mockito.verify(monitor, Mockito.times(2)).isCanceled();
		Mockito.verify(monitor).println(update);
		Mockito.verify(monitor).close();
		
		Mockito.verifyNoMoreInteractions(connector);
		Mockito.verifyNoMoreInteractions(monitor);
	}
	
}
