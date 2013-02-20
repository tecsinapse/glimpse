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

package br.com.tecsinapse.glimpse.client;

import java.util.Map;

import org.testng.collections.Maps;

import br.com.tecsinapse.glimpse.protocol.BeginPollResultItem;
import br.com.tecsinapse.glimpse.protocol.CancelPollResultItem;
import br.com.tecsinapse.glimpse.protocol.ClosePollResultItem;
import br.com.tecsinapse.glimpse.protocol.PollResult;
import br.com.tecsinapse.glimpse.protocol.PollResultItem;
import br.com.tecsinapse.glimpse.protocol.StreamUpdatePollResultItem;
import br.com.tecsinapse.glimpse.protocol.WorkedPollResultItem;

public class PollResultApplier {

	private Map<Class<? extends PollResultItem>, PollResultItemApplier<? extends PollResultItem>> appliersByClass = Maps.newHashMap();
	
	public PollResultApplier() {
		appliersByClass.put(BeginPollResultItem.class, new BeginPollResultItemApplier());
		appliersByClass.put(CancelPollResultItem.class, new CancelPollResultItemApplier());
		appliersByClass.put(ClosePollResultItem.class, new ClosePollResultItemApplier());
		appliersByClass.put(StreamUpdatePollResultItem.class, new StreamUpdatePollResultItemApplier());
		appliersByClass.put(WorkedPollResultItem.class, new WorkedPollResultItemApplier());
	}
	
	public void apply(PollResult result, Monitor monitor) {
		for (PollResultItem item : result.getPolls()) {
			PollResultItemApplier applier = appliersByClass.get(item.getClass());
			applier.apply(item, monitor);
		}
	}
	
}
