package br.com.tecsinapse.glimpse.http

import br.com.tecsinapse.glimpse.GlimpseShell
import br.com.tecsinapse.glimpse.Output
import groovy.json.JsonSlurper
import groovy.transform.EqualsAndHashCode

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

import static groovy.json.JsonOutput.toJson

@EqualsAndHashCode
class HttpGlimpseShell implements GlimpseShell {

    private String id;

    private HttpHandler httpHandler;

    private ExecutorService executor = Executors.newFixedThreadPool(1);

    private JsonSlurper slurper = new JsonSlurper()

    private int pollDelay

    HttpGlimpseShell(HttpHandler httpHandler, String id, int pollDelay) {
        this.httpHandler = httpHandler
        this.id = id
        this.pollDelay = pollDelay;
    }

    @Override
    void setParameter(String paramName, String value) {
        def params = [:]
        params.put(paramName, value)
        httpHandler.handle(toJson([operation: "set-parameters", id: id, params: params]))
    }

    @Override
    Future<String> evaluate(String script, Output output) {
        return executor.submit({
            httpHandler.handle(toJson([operation: 'evaluate', id: id, script: script]))
            while (true) {
                sleep(pollDelay)
                def result = slurper.parseText(httpHandler.handle(toJson([operation: 'poll-evaluate', id: id])))
                if (result.done) {
                    return result."return"
                }
            }
        } as Callable)
    }
}
