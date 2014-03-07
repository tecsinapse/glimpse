package br.com.tecsinapse.glimpse.http

import br.com.tecsinapse.glimpse.GlimpseServer
import br.com.tecsinapse.glimpse.GlimpseShell
import groovy.json.JsonSlurper

import static groovy.json.JsonOutput.toJson

class HttpGlimpseServer implements GlimpseServer {

    private JsonSlurper jsonSlurper = new JsonSlurper()

    private HttpHandler httpHandler;

    private int pollDelay

    HttpGlimpseServer(HttpHandler httpHandler, int pollDelay) {
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
