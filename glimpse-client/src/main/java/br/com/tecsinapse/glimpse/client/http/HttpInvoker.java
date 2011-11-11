package br.com.tecsinapse.glimpse.client.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class HttpInvoker {

	private String url;
	
	public HttpInvoker(String url) {
		this.url = url;
	}
	
	public String invoke(String context, String body) {
		try {
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod(url + context);
			post.setRequestEntity(new StringRequestEntity(body, "text/plain",
					"UTF-8"));
			int statusCode = client.executeMethod(post);
			String result = null;
			if (statusCode == HttpStatus.SC_OK) {
				StringBuilder builder = new StringBuilder();
				InputStream in = post.getResponseBodyAsStream();
				int c = 0;
				while ((c = in.read()) != -1) {
					builder.append((char) c);
				}
				result = builder.toString();
			}
			post.releaseConnection();
			if (result == null) {
				throw new UnsupportedOperationException();
			}
			return result;
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		} catch (HttpException e) {
			throw new IllegalStateException(e);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
}
