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
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import br.com.tecsinapse.glimpse.server.Server;

public class StartOpTest {
  
  @Test
  public void execute() {
	  String id = "myId";
	  String script = "myscript";
	  StartOp startOp = new StartOp(script);
	  
	  Server server = mock(Server.class);
	  when(server.start(script)).thenReturn(id);
	  
	  StartResult result = startOp.execute(server);
	  
	  assertEquals(result.getJobId(), id);
  }
  
}
