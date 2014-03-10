package br.com.tecsinapse.glimpse.http

import groovy.json.JsonSlurper
import spock.lang.Specification

import java.util.concurrent.atomic.AtomicBoolean

import static groovy.json.JsonOutput.toJson

class HttpGlimpseShellTest extends Specification {

    def pollDelay = 500

    def "set parameters"() {
        setup:
        def id = "1"
        def handler = Mock(HttpHandler.class)
        def shell = new HttpGlimpseShell(handler, id, pollDelay)

        when:
        shell.setParameter("param1", "value1")

        then:
        1 * handler.handle(toJson([operation: "set-parameters", id: id, params: [param1: "value1"]]))
    }

    def "evaluate"() {
        setup:
        def slurper = new JsonSlurper()
        def id = "1"
        def script = "script"
        def result = "result"
        def done = new AtomicBoolean(false)
        def handler = { input ->
            if (slurper.parseText(input) == [operation: 'evaluate', id: id, script: script]) {
                return toJson([result: "ok"])
            } else if (slurper.parseText(input) == [operation: 'poll-evaluate', id: id]) {
                if (done.get()) {
                    return toJson([result: "ok", done: true, "return": result])
                } else {
                    return toJson([result: "ok", done: false])
                }
            }
        } as HttpHandler
        def shell = new HttpGlimpseShell(handler, id, pollDelay)

        when:
        def future = shell.evaluate(script, null)

        then:
        !future.isDone()
        done.set(true)
        future.get() == result
    }

}
