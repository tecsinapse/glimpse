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

package br.com.tecsinapse.glimpse.client.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import br.com.tecsinapse.glimpse.client.BeginPoll;
import br.com.tecsinapse.glimpse.client.CancelPoll;
import br.com.tecsinapse.glimpse.client.ClientPoll;
import br.com.tecsinapse.glimpse.client.ClosePoll;
import br.com.tecsinapse.glimpse.client.StreamUpdatePoll;
import br.com.tecsinapse.glimpse.client.WorkedPoll;

public class ClientPollConverter {

	private static Set<String> commands = new HashSet<String>(Arrays.asList(
			"cancel", "close", "begin", "worked", "update"));

	public static List<ClientPoll> convert(String body) {
		List<ClientPoll> result = new LinkedList<ClientPoll>();
		StringReader reader = new StringReader(body);
		BufferedReader br = new BufferedReader(reader);
		try {
			String line = br.readLine();
			do {
				if (line != null) {
					if ("cancel".equals(line)) {
						result.add(new CancelPoll());
						line = br.readLine();
					} else if ("close".equals(line)) {
						result.add(new ClosePoll());
						line = br.readLine();
					} else if ("begin".equals(line)) {
						String steps = br.readLine();
						result.add(new BeginPoll(Integer.parseInt(steps)));
						line = br.readLine();
					} else if ("worked".equals(line)) {
						String steps = br.readLine();
						line = br.readLine();
						result.add(new WorkedPoll(Integer.parseInt(steps)));
					} else if ("update".equals(line)) {
						String update = br.readLine();
						do {
							result.add(new StreamUpdatePoll(update));
							update = br.readLine();
						} while (update != null && !commands.contains(update));
						line = update;
					}
				}
			} while (line != null);
		} catch (NumberFormatException e) {
			throw new IllegalStateException(e);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return result;
	}
}
