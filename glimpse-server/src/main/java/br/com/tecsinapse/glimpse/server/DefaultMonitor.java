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

package br.com.tecsinapse.glimpse.server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import br.com.tecsinapse.glimpse.protocol.BeginPollResultItem;
import br.com.tecsinapse.glimpse.protocol.PollResultItem;
import br.com.tecsinapse.glimpse.protocol.StreamUpdatePollResultItem;
import br.com.tecsinapse.glimpse.protocol.WorkedPollResultItem;


public class DefaultMonitor implements Monitor {

	private BlockingQueue<PollResultItem> queue;
	private AtomicBoolean canceled;
	
	public DefaultMonitor(BlockingQueue<PollResultItem> queue, AtomicBoolean canceled) {
		this.queue = queue;
		this.canceled = canceled;
	}

	public void begin(int steps) {
		try {
			queue.put(new BeginPollResultItem(steps));
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

	public void worked(int workedSteps) {
		try {
			queue.put(new WorkedPollResultItem(workedSteps));
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

	public boolean isCanceled() {
		return canceled.get();
	}

	public void println(Object output) {
		try {
			String text = nullSafeToString(output);
			queue.put(new StreamUpdatePollResultItem(text));
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

	private String nullSafeToString(Object output) {
		return output == null ? "null" : output.toString();
	}

}
