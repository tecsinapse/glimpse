package br.com.tecsinapse.glimpse.client.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class HttpInvoker {

	private String url;
	private String username;
	private String password;

	public HttpInvoker(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public String invoke(String context, String body) {
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
			int c = 0;
			while ((c = in.read()) != -1) {
				builder.append((char) c);
			}
			post.releaseConnection();
			if (statusCode == HttpStatus.SC_OK) {
				return builder.toString();
			} else if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
				return "update\nStatus: "
						+ statusCode
						+ "\nUnauthorized Access, check your username and password.\nclose\n";
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
