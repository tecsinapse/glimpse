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

import br.com.tecsinapse.glimpse.protocol.BeginPollResultItem;
import br.com.tecsinapse.glimpse.protocol.CancelPollResultItem;
import br.com.tecsinapse.glimpse.protocol.ClosePollResultItem;
import br.com.tecsinapse.glimpse.protocol.PollResultItem;
import br.com.tecsinapse.glimpse.protocol.StreamUpdatePollResultItem;
import br.com.tecsinapse.glimpse.protocol.WorkedPollResultItem;

public class ClientPollConverter {

	private static Set<String> commands = new HashSet<String>(Arrays.asList(
			"cancel", "close", "begin", "worked", "update"));

	public static List<PollResultItem> convert(String body) {
		List<PollResultItem> result = new LinkedList<PollResultItem>();
		StringReader reader = new StringReader(body);
		BufferedReader br = new BufferedReader(reader);
		try {
			String line = br.readLine();
			do {
				if (line != null) {
					if ("cancel".equals(line)) {
						result.add(new CancelPollResultItem());
						line = br.readLine();
					} else if ("close".equals(line)) {
						result.add(new ClosePollResultItem());
						line = br.readLine();
					} else if ("begin".equals(line)) {
						String steps = br.readLine();
						result.add(new BeginPollResultItem(Integer.parseInt(steps)));
						line = br.readLine();
					} else if ("worked".equals(line)) {
						String steps = br.readLine();
						line = br.readLine();
						result.add(new WorkedPollResultItem(Integer.parseInt(steps)));
					} else if ("update".equals(line)) {
						String update = br.readLine();
						do {
							result.add(new StreamUpdatePollResultItem(update));
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
