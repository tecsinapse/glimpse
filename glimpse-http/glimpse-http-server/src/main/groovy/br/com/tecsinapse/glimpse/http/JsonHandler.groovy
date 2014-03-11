package br.com.tecsinapse.glimpse.http

import br.com.tecsinapse.glimpse.Glimpse
import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import groovy.json.JsonSlurper

import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

import static groovy.json.JsonOutput.toJson

class JsonHandler {

    private JsonSlurper jsonSlurper = new JsonSlurper()

    private Glimpse glimpse

    private Cache<String, Future> evaluationFutures = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build();
    private Cache<String, JsonOutput> outputs = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build()

    JsonHandler(Glimpse glimpse) {
        this.glimpse = glimpse
    }

    String handle(String body) {
        def input = jsonSlurper.parseText(body)

        if (input.operation == 'create') {
            def id = glimpse.createShell()
            def result = [result: "ok", id: id]
            return toJson(result)
        } else if (input.operation == 'destroy') {
            def id = input.id
            glimpse.destroyShell(id)
            return toJson([result: 'ok'])
        } else if (input.operation == 'set-parameters') {
            def id = input.id
            def params = input.params
            def shell = glimpse.getShell(id)
            params.each { param, value ->
                shell.setParameter(param, value)
            }
            return toJson([result: 'ok'])
        } else if (input.operation == 'evaluate') {
            def id = input.id
            def script = input.script
            def shell = glimpse.getShell(id)
            def output = new JsonOutput()
            outputs.put(id, output)
            def future = shell.evaluate(script, output)
            evaluationFutures.put(id, future)
            return toJson([result: 'ok'])
        } else if (input.operation == 'poll-evaluate') {
            def id = input.id
            def future = evaluationFutures.getIfPresent(id)
            def output = outputs.getIfPresent(id)
            def result = [result: 'ok', done: future.isDone()] + output.getNextOutput()
            if (result.done) {
                result."return" = future.get()
            }
            return toJson(result)
        }

        throw new IllegalArgumentException("invalid body: ${body}")
    }
}