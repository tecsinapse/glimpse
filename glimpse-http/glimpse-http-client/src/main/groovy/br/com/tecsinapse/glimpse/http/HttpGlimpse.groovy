package br.com.tecsinapse.glimpse.http

import br.com.tecsinapse.glimpse.Glimpse
import br.com.tecsinapse.glimpse.GlimpseShell
import groovy.json.JsonSlurper

import static groovy.json.JsonOutput.toJson

class HttpGlimpse implements Glimpse {

    private JsonSlurper jsonSlurper = new JsonSlurper()

    private HttpHandler httpHandler;

    private int pollDelay

    HttpGlimpse(HttpHandler httpHandler, int pollDelay) {
        this.httpHandler = httpHandler
        this.pollDelay = pollDelay
    }

    @Override
    String createShell() {
        def output = httpHandler.handle(toJson([operation: "create"]))
        return jsonSlurper.parseText(output).id
    }

    @Override
    GlimpseShell getShell(String id) {
        return new HttpGlimpseShell(httpHandler, id, pollDelay)
    }

    @Override
    void destroyShell(String id) {
        httpHandler.handle(toJson([operation: "destroy", id: id]))
    }
}
