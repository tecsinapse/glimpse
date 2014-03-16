package br.com.tecsinapse.glimpse.cli

import spock.lang.Specification

class BootstrapTest extends Specification {

    def console = Mock(Console.class)

    def setup() {
        ConsoleFactory.consoleClosure = { console }
    }

    def "start console"() {
        setup:
        def connection = Mock(Connection.class)
        ConnectionFactory.connectionClosure = { connection }

        when:
        Bootstrap.main([] as String[])

        then:
        1 * console.start(connection)
    }

    def "run script"() {
        setup:
        def connection = Mock(Connection.class)
        ConnectionFactory.connectionClosure = { connection }
        def script = "script.groovy"

        when:
        Bootstrap.main([script] as String[])

        then:
        1 * console.runScript(connection, script)
    }

    // yet to implement
    /*def "start console with options"(options, connection) {
        when:
        Bootstrap.main(options as String[])

        then:
        1 * console.start(connection)


        where:
        options | connection
        ["-h", "hostName"] || new HostConnection("hostName")
    }*/

}
