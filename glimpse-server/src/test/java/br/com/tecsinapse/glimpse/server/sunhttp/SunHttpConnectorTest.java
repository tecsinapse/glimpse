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

package br.com.tecsinapse.glimpse.server.sunhttp;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import br.com.tecsinapse.glimpse.server.Server;

public class SunHttpConnectorTest {

	@Test
	public void operationHandler() throws Exception {
		String jobId = "myJobId";
		String script = "myScript";

		Server server = mock(Server.class);
		Mockito.when(server.start(script)).thenReturn(jobId);

		SunHttpConnector connector = new SunHttpConnector(server, 8081);
		connector.start();

		String startXml = String.format("<start><script>%s</script></start>",
				script);

		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://localhost:8081/");
		post.setRequestEntity(new StringRequestEntity(startXml, "text/plain",
				"UTF-8"));
		int statusCode = client.executeMethod(post);
		StringBuilder builder = new StringBuilder();
		InputStream in = post.getResponseBodyAsStream();
		if (in != null) {
			int c = 0;
			while ((c = in.read()) != -1) {
				builder.append((char) c);
			}
		}
		post.releaseConnection();
		if (statusCode != HttpStatus.SC_OK) {
			fail(builder.toString());
		}

		String expected = String
				.format("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><start-result><job-id>%s</job-id></start-result>",
						jobId);

		assertEquals(expected, builder.toString());

		connector.stop();
	}
}
