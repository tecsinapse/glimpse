package br.com.tecsinapse.glimpse.http

import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.HttpStatus
import org.apache.commons.httpclient.methods.PostMethod
import org.apache.commons.httpclient.methods.StringRequestEntity

class HttpClientHttpHandler implements HttpHandler {

    private String url;

    private HttpClient client = new HttpClient()

    HttpClientHttpHandler(String host, int port) {
        this.url = "http://${host}:${port}/"
    }

    @Override
    String handle(String input) {
        def post = new PostMethod(url)
        post.setRequestEntity(new StringRequestEntity(input, "application/json", "UTF-8"))
        int statusCode = client.executeMethod(post)
        def body = post.getResponseBodyAsString()
        post.releaseConnection()
        if (statusCode == HttpStatus.SC_OK) {
            return body
        } else {
            throw new IllegalStateException()
        }
    }
}
