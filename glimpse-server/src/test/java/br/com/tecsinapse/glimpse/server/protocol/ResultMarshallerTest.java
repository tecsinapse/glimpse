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

public class ResultMarshallerTest {

	private ResultMarshaller marshaller = new ResultMarshaller();

	@Test
	public void startResult() {
		String jobId = "myJobId";
		StartResult startResult = new StartResult(jobId);

		String expected = String
				.format("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><start-result><job-id>%s</job-id></start-result>",
						jobId);
		assertEquals(marshaller.marshall(startResult), expected);
	}

}
