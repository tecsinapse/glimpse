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

import java.util.ArrayList;
import java.util.List;

import br.com.tecsinapse.glimpse.server.ServerPoll;

public class PollResult implements Result {

	private List<ServerPoll> polls = new ArrayList<ServerPoll>();
	
	public PollResult() {
	}
	
	public PollResult(List<ServerPoll> polls) {
		this.polls = new ArrayList<ServerPoll>(polls);
	}
	
	public List<ServerPoll> getPolls() {
		return polls;
	}
	
}
