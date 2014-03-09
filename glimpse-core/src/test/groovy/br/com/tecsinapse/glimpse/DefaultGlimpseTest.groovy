package br.com.tecsinapse.glimpse

import spock.lang.Specification

class DefaultGlimpseTest extends Specification {

    def factory = Mock(GlimpseShellFactory.class)
    def glimpse = new DefaultGlimpse(factory)

    def "create shell"() {
        setup:
        def shell = Mock(GlimpseShell.class)
        factory.create() >> shell

        when:
        def id = glimpse.createShell()

        then:
        glimpse.getShell(id) == shell
    }

    def "destroy shell"() {
        setup:
        def shell = Mock(GlimpseShell.class)
        factory.create() >> shell
        def id = glimpse.createShell()

        when:
        glimpse.destroyShell(id)

        then:
        glimpse.getShell(id) == null
    }
}
