package br.com.tecsinapse.glimpse.http

import spock.lang.Specification

import static groovy.json.JsonOutput.toJson

class HttpGlimpseServerTest extends Specification {

    def handler = Mock(HttpHandler.class)
    def server = new HttpGlimpseServer(handler, 500)

    def "create shell"() {
        setup:
        def id = "1"
        handler.handle(toJson([operation: 'create'])) >> toJson([result: "ok", id: id])

        expect:
        server.createShell() == id
    }

    def "get shell"() {
        setup:
        def id = "1"

        expect:
        server.getShell(id) == new HttpGlimpseShell(handler, id, 500)
    }

    def "destroy shell"() {
        setup:
        def id = "1"

        when:
        server.destroyShell(id)

        then:
        1 * handler.handle(toJson([operation: 'destroy', id: id]))
    }

}
