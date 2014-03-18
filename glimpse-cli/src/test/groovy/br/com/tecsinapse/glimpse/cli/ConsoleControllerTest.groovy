package br.com.tecsinapse.glimpse.cli

import br.com.tecsinapse.glimpse.GlimpseShell
import br.com.tecsinapse.glimpse.Output
import spock.lang.Specification

import java.util.concurrent.Callable
import java.util.concurrent.Executors

class ConsoleControllerTest extends Specification {

    def connection = Mock(Connection.class)
    def controller = new ConsoleController(connection)

    def "next prompt"() {
        setup:
        def description = "host_desc"
        connection.getDescription() >> description

        expect:
        "${description}>" == controller.nextPrompt()
    }

    def "backslash command"() {
        setup:
        def message = "message"
        def commands = [
            "\\command": [
                "run": { w ->
                    w.println(message)
                }
            ] as Command
        ]
        controller.commands = commands
        def writer = new StringWriter()

        when:
        controller.execute("\\command", new PrintWriter(writer))

        then:
        "${message}\n" == writer.toString()
    }

    def "groovy command"() {
        setup:
        def command = "1 + 1"
        def result = "2"
        def writer = new StringWriter()
        def executor = Executors.newFixedThreadPool(1)
        def shell = [
            evaluate: { script, output ->
               executor.submit({
                   result
               } as Callable)
            }
        ] as GlimpseShell
        connection.getShell() >> shell

        when:
        controller.execute(command, new PrintWriter(writer))

        then:
        "===> ${result}\n" == writer.toString()
    }

}
