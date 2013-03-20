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

import java.util.Arrays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MarshallerTest {

	@Test
	public void startOp() {
		String script = "myScript";
		StartOp startOp = new StartOp(script);

		String expected = String.format("%s<start><script>%s</script></start>",
				Utils.XML_HEADER, script);
		assertEquals(Marshaller.marshall(startOp), expected);
	}
	
	@Test
	public void cancelOp() {
		String jobId = "myJobId";
		CancelOp cancelOp = new CancelOp(jobId);
		
		String expected = String.format("%s<cancel><job-id>%s</job-id></cancel>", Utils.XML_HEADER, jobId);
		assertEquals(Marshaller.marshall(cancelOp), expected);
	}

	@Test
	public void startResult() {
		String jobId = "myJobId";
		StartResult startResult = new StartResult(jobId);

		String expected = String.format(
				"%s<start-result><job-id>%s</job-id></start-result>",
				Utils.XML_HEADER, jobId);
		assertEquals(Marshaller.marshall(startResult), expected);
	}

	@Test
	public void pollOp() {
		String jobId = "myJobId";
		PollOp pollOp = new PollOp(jobId);

		String expected = String.format("%s<poll><job-id>%s</job-id></poll>",
				Utils.XML_HEADER, jobId);
		
		assertEquals(Marshaller.marshall(pollOp), expected);
	}

	@DataProvider(name = "poll-result")
	public Object[][] createPollResultData() {
		int steps = 10;
		int workedSteps = 1;
		String simpleUpdate = "update";
		String updateWithXmlText = "<tag>\n\t<inner-tag/>\n\t</tag>";
		String normalizedUpdateWithXmlText = "&lt;tag&gt;\n\t&lt;inner-tag/&gt;\n\t&lt;/tag&gt;";
		return new Object[][] {
				{
						new BeginPollResultItem(steps),
						String.format(
								"%s<poll-result><begin><steps>%d</steps></begin></poll-result>",
								Utils.XML_HEADER, steps) },
				{
						new CancelPollResultItem(),
						String.format("%s<poll-result><canceled/></poll-result>",
								Utils.XML_HEADER) },
				{
						new ClosePollResultItem(),
						String.format("%s<poll-result><close/></poll-result>",
								Utils.XML_HEADER) },
				{
						new StreamUpdatePollResultItem(simpleUpdate),
						String.format(
								"%s<poll-result><stream-update>%s</stream-update></poll-result>",
								Utils.XML_HEADER, simpleUpdate) },
				{
						new StreamUpdatePollResultItem(updateWithXmlText),
						String.format(
								"%s<poll-result><stream-update>%s</stream-update></poll-result>",
								Utils.XML_HEADER, normalizedUpdateWithXmlText) },
				{
						new WorkedPollResultItem(workedSteps),
						String.format(
								"%s<poll-result><worked><steps>%d</steps></worked></poll-result>",
								Utils.XML_HEADER, workedSteps) } };
	}

	@Test(dataProvider = "poll-result")
	public void pollResult(PollResultItem poll, String expected) {
		PollResult pollResult = new PollResult(Arrays.asList(poll));

		assertEquals(Marshaller.marshall(pollResult), expected);
	}

}
