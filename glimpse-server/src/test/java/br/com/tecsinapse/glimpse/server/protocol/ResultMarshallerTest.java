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

import java.util.Arrays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import br.com.tecsinapse.glimpse.server.BeginPoll;
import br.com.tecsinapse.glimpse.server.CancelPoll;
import br.com.tecsinapse.glimpse.server.ClosePoll;
import br.com.tecsinapse.glimpse.server.ServerPoll;
import br.com.tecsinapse.glimpse.server.Utils;

public class ResultMarshallerTest {

	private ResultMarshaller marshaller = new ResultMarshaller();

	@Test
	public void startResult() {
		String jobId = "myJobId";
		StartResult startResult = new StartResult(jobId);

		String expected = String.format(
				"%s<start-result><job-id>%s</job-id></start-result>",
				Utils.XML_HEADER, jobId);
		assertEquals(marshaller.marshall(startResult), expected);
	}

	@DataProvider(name = "poll-result")
	public Object[][] createPollResultData() {
		int steps = 10;
		return new Object[][] { 
				{ new BeginPoll(steps), String.format("%s<poll-result><begin><steps>%d</steps></begin></poll-result>", Utils.XML_HEADER, steps) },
				{ new CancelPoll(), String.format("%s<poll-result><cancel/></poll-result>", Utils.XML_HEADER) },
				{ new ClosePoll(), String.format("%s<poll-result><close/></poll-result>", Utils.XML_HEADER) }
		};
	}

	@Test(dataProvider = "poll-result")
	public void pollResult(ServerPoll poll, String expected) {
		PollResult pollResult = new PollResult(
				Arrays.asList(poll));

		assertEquals(marshaller.marshall(pollResult), expected);
	}

}
