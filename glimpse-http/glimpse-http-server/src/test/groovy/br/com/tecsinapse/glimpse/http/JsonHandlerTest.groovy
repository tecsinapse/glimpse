package br.com.tecsinapse.glimpse.http

import br.com.tecsinapse.glimpse.Glimpse
import br.com.tecsinapse.glimpse.GlimpseShell
import groovy.json.JsonSlurper
import spock.lang.Specification

import java.util.concurrent.Future

import static groovy.json.JsonOutput.toJson

class JsonHandlerTest extends Specification {

    def slurper = new JsonSlurper()
    def glimpse = Mock(Glimpse.class)
    def jsonHandler = new JsonHandler(glimpse)

    def "create shell"() {
        setup:
        def id = "1"
        def input = toJson([operation: "create"])
        glimpse.createShell() >> 1

        expect:
        slurper.parseText(jsonHandler.handle(input)) == [result: "ok", id: id]
    }

    def "destroy shell"() {
        setup:
        def id = "1"
        def input = toJson([operation: "destroy", id: id])

        when:
        def output = jsonHandler.handle(input)

        then:
        slurper.parseText(output) == [result: "ok"]
        1 * glimpse.destroyShell(id)
    }

    def "set parameters"() {
        setup:
        def id = "1"
        def shell = Mock(GlimpseShell.class)
        glimpse.getShell(id) >> shell
        def input = toJson([operation: "set-parameters", id: id, params: [param1: "value1", param2: "value2"]])

        when:
        def output = jsonHandler.handle(input)

        then:
        slurper.parseText(output) == [result: "ok"]
        1 * shell.setParameter("param1", "value1")
        1 * shell.setParameter("param2", "value2")
    }

    def "evaluate"() {
        setup:
        def id = "1"
        def shell = Mock(GlimpseShell.class)
        glimpse.getShell(id) >> shell
        def script = "script"
        def future = Mock(Future.class)
        shell.evaluate(script, _) >> future
        def input = toJson([operation: "evaluate", id: id, script: script])

        when:
        def output = jsonHandler.handle(input)

        then:
        slurper.parseText(output) == [result: "ok"]
    }

    def "poll evaluate incomplete"() {
        setup:
        def id = "1"
        def future = Mock(Future.class)
        future.isDone() >> false
        def shell = Mock(GlimpseShell.class)
        def script = "script"
        shell.evaluate(script, _) >> future
        glimpse.getShell(id) >> shell
        jsonHandler.handle(toJson([operation: "evaluate", id: id, script: script]))
        def input = toJson([operation: "poll-evaluate", id: id])

        when:
        def output = jsonHandler.handle(input)

        then:
        slurper.parseText(output) == [result: "ok", done: false]
    }

    def "poll evaluate complete"() {
        setup:
        def id = "1"
        def script = "script"
        def result = "result"
        def future = Mock(Future.class)
        future.isDone() >> true
        future.get() >> result
        def shell = Mock(GlimpseShell.class)
        shell.evaluate(script, _) >> future
        glimpse.getShell(id) >> shell
        jsonHandler.handle(toJson([operation: "evaluate", id: id, script: script]))
        def input = toJson([operation: "poll-evaluate", id: id])

        when:
        def output = jsonHandler.handle(input)

        then:
        slurper.parseText(output) == [result: "ok", done: true, "return": result]
    }

    def "poll evaluate stream redirection"() {
        setup:
        def id = "1"
        def script = "script"
        def message = "message"
        def future = Mock(Future.class)
        future.isDone() >> false
        def shell = [
            evaluate: { s, o ->
                assert s == script
                o.println(message)
                return future
            }
        ] as GlimpseShell
        glimpse.getShell(id) >> shell
        jsonHandler.handle(toJson([operation: "evaluate", id: id, script: script]))
        def input = toJson([operation: "poll-evaluate", id: id])

        when:
        def json = jsonHandler.handle(input)

        then:
        slurper.parseText(json) == [result: "ok", done: false, "print": "${message}\n"]
    }

}
