package br.com.tecsinapse.glimpse.client.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
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
			StringBuilder builder = new StringBuilder();
			InputStream in = post.getResponseBodyAsStream();
			int c = 0;
			while ((c = in.read()) != -1) {
				builder.append((char) c);
			}
			post.releaseConnection();
			if (statusCode == HttpStatus.SC_OK) {
				return builder.toString();
			} else {
				return "update\nStatus: " + statusCode + "\n"
						+ builder.toString() + "\nclose\n";
			}
		} catch (UnsupportedEncodingException e) {
			return exceptionResult(e);
		} catch (HttpException e) {
			return exceptionResult(e);
		} catch (IOException e) {
			return exceptionResult(e);
		}
	}

	private String exceptionResult(Exception e) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		return "update\n" + stringWriter.toString() + "\nclose\n";
	}

}
