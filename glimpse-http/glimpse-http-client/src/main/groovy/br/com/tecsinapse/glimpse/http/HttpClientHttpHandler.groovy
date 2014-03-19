package br.com.tecsinapse.glimpse.http

import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.HttpStatus
import org.apache.commons.httpclient.methods.PostMethod
import org.apache.commons.httpclient.methods.StringRequestEntity

class HttpClientHttpHandler implements HttpHandler {

    private String url

    private String username

    private String password

    private HttpClient client = new HttpClient()

    HttpClientHttpHandler(String url, String username, String password) {
        this.url = url
        this.username = username
        this.password = password
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
