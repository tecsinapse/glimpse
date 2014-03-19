package br.com.tecsinapse.glimpse.cli

import br.com.tecsinapse.glimpse.GlimpseShell
import spock.lang.Specification

import java.util.concurrent.Callable
import java.util.concurrent.Executors

class ConsoleControllerTest extends Specification {

    def connection = Mock(Connection.class)
    def fileSystem = Mock(FileSystem.class)
    def controller = new ConsoleController(connection, fileSystem)

    def "next prompt"() {
        setup:
        def description = "host_desc"
        connection.getDescription() >> description

        expect:
        "${description}> " == controller.nextPrompt()
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

    def "backslash command with question"() {
        setup:
        def message = "message"
        def count = 0
        def command = new Command() {
            @Override
            Command run(PrintWriter writer) {
                return this
            }

            @Override
            String nextPrompt() {
                throw new UnsupportedOperationException()
            }

            @Override
            Command answer(String answer, Writer writer) {
                if (count == 0) {
                    count++
                    return this
                } else {
                    writer.print(message)
                    return null
                }
            }
        }
        def commands = [
            "\\command": command
        ]
        controller.commands = commands
        def writer = new StringWriter()

        when:
        controller.execute("\\command", new PrintWriter(writer))
        controller.execute("1", new PrintWriter(writer))
        controller.execute("2", new PrintWriter(writer))

        then:
        "${message}" == writer.toString()
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

    def "quit command"() {
        when:
        controller.execute("\\quit", new PrintWriter(new StringWriter()))

        then:
        controller.isFinished()
    }

    def "execute script"() {
        setup:
        fileSystem.read("script.groovy") >> "1 + 1"
        def result = null
        def shell = [
            evaluate: { s, o ->
                return Executors.newFixedThreadPool(1).submit({
                    result = Eval.me(s)
                    return result.toString()
                } as Callable)
            }
        ] as GlimpseShell
        connection.getShell() >> shell
        def writer = new StringWriter()

        when:
        controller.executeScript("script.groovy", new PrintWriter(writer))

        then:
        result == 2
        writer.toString() == "Executing script at: null\n" +
                "----------------------------------------------\n" +
                "\n" +
                "===> 2\n"
    }

}
