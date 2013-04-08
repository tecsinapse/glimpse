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

package br.com.tecsinapse.glimpse.protocol;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;

import br.com.tecsinapse.glimpse.protocol.Parser;
import br.com.tecsinapse.glimpse.protocol.PollOp;
import br.com.tecsinapse.glimpse.protocol.StartOp;

public class ParserTest {

	@Test
	public void startResult() {
		String jobId = "myJobId";
		String xml = String.format(
				"<start-result><job-id>%s</job-id></start-result>", jobId);
		StartResult result = (StartResult) Parser.parse(xml);
		assertEquals(result.getJobId(), jobId);
	}

	@Test
	public void start() {
		String script = "myscript";
		String xml = String
				.format("<start><script>%s</script></start>", script);

		StartOp startOp = (StartOp) Parser.parse(xml);
		assertEquals(startOp.getScript(), script);
	}

	@Test
	public void poll() {
		String jobId = "myJobId";
		String xml = String.format("<poll><job-id>%s</job-id></poll>", jobId);

		PollOp pollOp = (PollOp) Parser.parse(xml);
		assertEquals(pollOp.getJobId(), jobId);
	}

	@Test
	public void cancel() {
		String jobId = "myJobId";
		String xml = String.format("<cancel><job-id>%s</job-id></cancel>",
				jobId);

		CancelOp cancelOp = (CancelOp) Parser.parse(xml);
		assertEquals(cancelOp.getJobId(), jobId);
	}

	@Test
	public void beginPollResultItem() {
		int steps = 10;
		String xml = String.format("<begin><steps>%d</steps></begin>", steps);

		BeginPollResultItem result = (BeginPollResultItem) Parser.parse(xml);
		assertEquals(result.getSteps(), steps);
	}

	@Test
	public void cancelPollResultItem() {
		String xml = "<canceled/>";
		assertTrue(Parser.parse(xml) instanceof CancelPollResultItem);
	}

	@Test
	public void closePollResultItem() {
		String xml = "<close/>";
		assertTrue(Parser.parse(xml) instanceof ClosePollResultItem);
	}

	@Test
	public void streamUpdatePollResultItem() {
		String update = "hello";
		String xml = String.format("<stream-update>%s</stream-update>", update);

		StreamUpdatePollResultItem result = (StreamUpdatePollResultItem) Parser
				.parse(xml);
		assertEquals(result.getUpdate(), update);
	}

	@Test
	public void workedPollResultItem() {
		int steps = 1;
		String xml = String.format("<worked><steps>%d</steps></worked>", steps);

		WorkedPollResultItem result = (WorkedPollResultItem) Parser.parse(xml);
		assertEquals(result.getWorkedSteps(), steps);
	}

	@Test
	public void pollResult() {
		String xml = "<poll-result><close/></poll-result>";

		PollResult result = (PollResult) Parser.parse(xml);
		assertEquals(result.getItems().get(0), new ClosePollResultItem());
	}

	@Test
	public void createReplOp() {
		String xml = "<create-repl/>";
		assertEquals(Parser.parse(xml), new CreateReplOp());
	}

	@Test
	public void createReplResult() {
		String id = "myId";
		String xml = String
				.format("<create-repl-result><repl-id>%s</repl-id></create-repl-result>",
						id);
		assertEquals(Parser.parse(xml), new CreateReplResult(id));
	}

	@Test
	public void evalOp() {
		String replId = "myId";
		String expression = "myexpression";
		String xml = String
				.format("<eval><repl-id>%s</repl-id><expression>%s</expression></eval>",
						replId, expression);
		assertEquals(Parser.parse(xml), new EvalOp(replId, expression));
	}

	@Test
	public void evalResult() {
		String result = "myresult";
		String xml = String.format(
				"<eval-result><result>%s</result></eval-result>", result);
		assertEquals(Parser.parse(xml), new EvalResult(result));
	}

	@Test
	public void closeReplOp() {
		String replId = "myId";
		String xml = String.format(
				"<close-repl><repl-id>%s</repl-id></close-repl>", replId);
		assertEquals(Parser.parse(xml), new CloseReplOp(replId));
	}

	@Test
	public void closeReplResult() {
		String xml = "<close-repl-result/>";
		assertEquals(Parser.parse(xml), new CloseReplResult());
	}

}
