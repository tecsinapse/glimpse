package br.com.tecsinapse.glimpse.http

import spock.lang.Specification

import static groovy.json.JsonOutput.toJson

class HttpGlimpseTest extends Specification {

    def handler = Mock(HttpHandler.class)
    def glimpse = new HttpGlimpse(handler, 500)

    def "create shell"() {
        setup:
        def id = "1"
        handler.handle(toJson([operation: 'create'])) >> toJson([result: "ok", id: id])

        expect:
        glimpse.createShell() == id
    }

    def "get shell"() {
        setup:
        def id = "1"

        expect:
        glimpse.getShell(id) == new HttpGlimpseShell(handler, id, 500)
    }

    def "destroy shell"() {
        setup:
        def id = "1"

        when:
        glimpse.destroyShell(id)

        then:
        1 * handler.handle(toJson([operation: 'destroy', id: id]))
    }

}
