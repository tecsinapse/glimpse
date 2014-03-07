package br.com.tecsinapse.glimpse

import spock.lang.Specification

class DefaultGlimpseServerTest extends Specification {

    def factory = Mock(GlimpseShellFactory.class)
    def server = new DefaultGlimpseServer(factory)

    def "create shell"() {
        setup:
        def shell = Mock(GlimpseShell.class)
        factory.create() >> shell

        when:
        def id = server.createShell()

        then:
        server.getShell(id) == shell
    }

    def "destroy shell"() {
        setup:
        def shell = Mock(GlimpseShell.class)
        factory.create() >> shell
        def id = server.createShell()

        when:
        server.destroyShell(id)

        then:
        server.getShell(id) == null
    }
}
