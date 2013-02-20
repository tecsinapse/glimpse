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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="poll-result")
public class PollResult implements Result {

	@XmlAnyElement
	private List<ServerPoll> polls = new ArrayList<ServerPoll>();
	
	public PollResult() {
	}
	
	public PollResult(List<ServerPoll> polls) {
		this.polls = new ArrayList<ServerPoll>(polls);
	}
	
	public List<ServerPoll> getPolls() {
		return polls;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((polls == null) ? 0 : polls.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PollResult other = (PollResult) obj;
		if (polls == null) {
			if (other.polls != null)
				return false;
		} else if (!polls.equals(other.polls))
			return false;
		return true;
	}
	
}
