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

    def "start console"(options, result) {
        setup:
        def writer = new StringWriter()
        console.getWriter() >> new PrintWriter(writer)

        when:
        Bootstrap.main(options as String[])

        then:
        if (result instanceof Connection) {
            1 * console.start(result)
        } else {
            writer.toString().contains(result)
        }

        where:
        options             || result
        []                  || defaultConnection
        ["-h", "hostName"]  || new HostConnection("hostName")
        ["-h"]              || "Missing argument for option: h"
        ["-url", "url", "-u", "username", "-p", "password"] || new UrlConnection("url", "username", "password")
        ["-url", "url"]     || new UrlConnection("url", null, null)
    }

}
