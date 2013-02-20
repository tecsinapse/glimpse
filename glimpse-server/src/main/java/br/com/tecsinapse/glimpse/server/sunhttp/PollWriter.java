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

import br.com.tecsinapse.glimpse.protocol.BeginPoll;
import br.com.tecsinapse.glimpse.protocol.CancelPoll;
import br.com.tecsinapse.glimpse.protocol.ClosePoll;
import br.com.tecsinapse.glimpse.protocol.ServerPoll;
import br.com.tecsinapse.glimpse.protocol.StreamUpdatePoll;
import br.com.tecsinapse.glimpse.protocol.WorkedPoll;

public class PollWriter {

	public static String write(ServerPoll serverPoll) {
		if (serverPoll instanceof BeginPoll) {
			return "begin\n" + ((BeginPoll) serverPoll).getSteps();
		} else if (serverPoll instanceof CancelPoll) {
			return "cancel";
		} else if (serverPoll instanceof ClosePoll) {
			return "close";
		} else if (serverPoll instanceof WorkedPoll) {
			return "worked\n" + ((WorkedPoll) serverPoll).getWorkedSteps();
		} else if (serverPoll instanceof StreamUpdatePoll) {
			return "update\n" + ((StreamUpdatePoll) serverPoll).getUpdate();
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
}
