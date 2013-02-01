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

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class OperationParserTest {

	private OperationParser operationParser = new OperationParser();
	
	@Test
	public void start() {
		String script = "myscript";
		String xml = String
				.format("<start><script>%s</script></start>", script);

		StartOp startOp = (StartOp) operationParser.parse(xml);
		assertEquals(startOp.getScript(), script);
	}
	
	@Test
	public void poll() {
		String jobId = "myJobId";
		String xml = String.format("<poll><jobId>%s</jobId></poll>", jobId);
		
		PollOp pollOp = (PollOp) operationParser.parse(xml);
		assertEquals(pollOp.getJobId(), jobId);
	}

}
