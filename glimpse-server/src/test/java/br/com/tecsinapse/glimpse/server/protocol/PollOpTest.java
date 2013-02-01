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

package br.com.tecsinapse.glimpse.server.protocol;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import br.com.tecsinapse.glimpse.server.Server;
import br.com.tecsinapse.glimpse.server.ServerPoll;

public class PollOpTest {

	@Test
	public void execute() {
		String jobId = "myJobId";
		PollOp pollOp = new PollOp(jobId);
		
		List<ServerPoll> polls = new ArrayList<ServerPoll>();
		
		Server server = mock(Server.class);
		when(server.poll(jobId)).thenReturn(polls);
		
		pollOp.execute(server);
		
		verify(server);
	}
	
}
