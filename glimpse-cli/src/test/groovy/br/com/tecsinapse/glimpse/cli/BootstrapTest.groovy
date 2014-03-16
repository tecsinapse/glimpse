package br.com.tecsinapse.glimpse.cli

import spock.lang.Shared
import spock.lang.Specification

class BootstrapTest extends Specification {

    def console = Mock(Console.class)
    @Shared
    def defaultConnection = Mock(Connection.class)


    def setup() {
        ConsoleFactory.consoleClosure = { console }
        ConnectionFactory.defaultConnectionClosure = { defaultConnection }
    }

    def "run script"() {
        setup:
        def script = "script.groovy"

        when:
        Bootstrap.main([script] as String[])

        then:
        1 * console.runScript(defaultConnection, script)
    }

    def "start console"(options, connection) {
        when:
        Bootstrap.main(options as String[])

        then:
        1 * console.start(connection)

        where:
        options             || connection
        []                  || defaultConnection
        ["-h", "hostName"]  || new HostConnection("hostName")
    }

}
