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

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import br.com.tecsinapse.glimpse.server.BeginPoll;
import br.com.tecsinapse.glimpse.server.CancelPoll;
import br.com.tecsinapse.glimpse.server.ClosePoll;

public class ResultMarshaller {

	public String marshall(Result result) {
		try {
			JAXBContext context = JAXBContext.newInstance(StartResult.class,
					PollResult.class, BeginPoll.class, CancelPoll.class,
					ClosePoll.class);
			StringWriter writer = new StringWriter();
			context.createMarshaller().marshal(result, writer);
			return writer.toString();
		} catch (JAXBException e) {
			throw new IllegalStateException(e);
		}
	}

}
