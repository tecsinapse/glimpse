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

import br.com.tecsinapse.glimpse.protocol.BeginPollResultItem;
import br.com.tecsinapse.glimpse.protocol.CancelPollResultItem;
import br.com.tecsinapse.glimpse.protocol.ClosePollResultItem;
import br.com.tecsinapse.glimpse.protocol.PollResultItem;
import br.com.tecsinapse.glimpse.protocol.StreamUpdatePollResultItem;
import br.com.tecsinapse.glimpse.protocol.WorkedPollResultItem;

public class PollWriter {

	public static String write(PollResultItem serverPoll) {
		if (serverPoll instanceof BeginPollResultItem) {
			return "begin\n" + ((BeginPollResultItem) serverPoll).getSteps();
		} else if (serverPoll instanceof CancelPollResultItem) {
			return "cancel";
		} else if (serverPoll instanceof ClosePollResultItem) {
			return "close";
		} else if (serverPoll instanceof WorkedPollResultItem) {
			return "worked\n" + ((WorkedPollResultItem) serverPoll).getWorkedSteps();
		} else if (serverPoll instanceof StreamUpdatePollResultItem) {
			return "update\n" + ((StreamUpdatePollResultItem) serverPoll).getUpdate();
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
}
