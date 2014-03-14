package br.com.tecsinapse.glimpse.cli

import spock.lang.Specification

class BootstrapTest extends Specification {

    def "simple console"() {
        setup:
        def console = Mock(Console.class)
        ConsoleFactory.consoleClosure = { console }

        when:
        Bootstrap.main([] as String[])

        then:
        1 * console.start()
    }

}
