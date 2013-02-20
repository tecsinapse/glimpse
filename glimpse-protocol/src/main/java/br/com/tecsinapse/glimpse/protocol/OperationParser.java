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

import java.io.ByteArrayInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class OperationParser {

	public Operation parse(String xml) {
		try {
			JAXBContext context = JAXBContext.newInstance(StartOp.class, PollOp.class);
			return (Operation) context.createUnmarshaller().unmarshal(
					new ByteArrayInputStream(xml.getBytes()));
		} catch (JAXBException e) {
			throw new IllegalStateException(e);
		}
	}

}
