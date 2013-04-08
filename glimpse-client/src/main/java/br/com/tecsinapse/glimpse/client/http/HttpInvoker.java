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

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import br.com.tecsinapse.glimpse.client.ConnectorException;
import br.com.tecsinapse.glimpse.client.NotFoundException;
import br.com.tecsinapse.glimpse.client.UnauthorizedException;

public class HttpInvoker {

	private String url;
	private String username;
	private String password;

	public HttpInvoker(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public String invoke(String context, String body) throws ConnectorException {
		try {
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod(url + context);
			String base64 = username + ":" + password;
			post.setRequestHeader(
					"Authorization",
					"Basic "
							+ new String(Base64.encodeBase64(base64.getBytes())));
			post.setRequestEntity(new StringRequestEntity(body, "text/plain",
					"UTF-8"));
			int statusCode = client.executeMethod(post);
			StringBuilder builder = new StringBuilder();
			InputStream in = post.getResponseBodyAsStream();
			if (in != null) {
				int c = 0;
				while ((c = in.read()) != -1) {
					builder.append((char) c);
				}
			}
			post.releaseConnection();
			if (statusCode == HttpStatus.SC_OK) {
				return builder.toString();
			} else if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
				throw new UnauthorizedException(
						"Unauthorized Access, check your username and password.");
			} else if (statusCode == HttpStatus.SC_NOT_FOUND) {
				throw new NotFoundException(builder.toString());
			} else {
				throw new ConnectorException("Status: " + statusCode + " - "
						+ builder.toString());
			}
		} catch (UnsupportedEncodingException e) {
			throw new ConnectorException(e);
		} catch (HttpException e) {
			throw new ConnectorException(e);
		} catch (IOException e) {
			throw new ConnectorException(e);
		}
	}

}
