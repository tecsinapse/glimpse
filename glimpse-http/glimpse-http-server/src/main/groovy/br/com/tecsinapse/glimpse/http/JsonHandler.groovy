package br.com.tecsinapse.glimpse.http

import br.com.tecsinapse.glimpse.GlimpseServer
import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import groovy.json.JsonSlurper

import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

import static groovy.json.JsonOutput.toJson

class JsonHandler {

    private JsonSlurper jsonSlurper = new JsonSlurper()

    private GlimpseServer glimpseServer

    private Cache<String, Future> evaluationFutures = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build();

    JsonHandler(GlimpseServer glimpseServer) {
        this.glimpseServer = glimpseServer
    }

    String handle(String body) {
        def input = jsonSlurper.parseText(body)

        if (input.operation == 'create') {
            def id = glimpseServer.createShell()
            def result = [result: "ok", id: id]
            return toJson(result)
        } else if (input.operation == 'destroy') {
            def id = input.id
            glimpseServer.destroyShell(id)
            return toJson([result: 'ok'])
        } else if (input.operation == 'set-parameters') {
            def id = input.id
            def params = input.params
            def shell = glimpseServer.getShell(id)
            params.each { param, value ->
                shell.setParameter(param, value)
            }
            return toJson([result: 'ok'])
        } else if (input.operation == 'evaluate') {
            def id = input.id
            def script = input.script
            def shell = glimpseServer.getShell(id)
            def future = shell.evaluate(script)
            evaluationFutures.put(id, future)
            return toJson([result: 'ok'])
        } else if (input.operation == 'poll-evaluate') {
            def id = input.id
            def future = evaluationFutures.getIfPresent(id)
            def result = [result: 'ok', done: future.isDone()]
            if (result.done) {
                result."return" = future.get()
            }
            return toJson(result)
        }

        throw new IllegalArgumentException("invalid body: ${body}")
    }
}