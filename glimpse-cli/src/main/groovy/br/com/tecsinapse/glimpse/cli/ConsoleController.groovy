package br.com.tecsinapse.glimpse.cli

public class ConsoleController {

    private Connection connection;
    Map<String, Command> commands

    ConsoleController(Connection connection) {
        this.connection = connection;
    }

    String nextPrompt() {
        return "${connection.description}>"
    }

    void execute(String command, PrintWriter writer) {
        def c = command.startsWith("\\") ? commands.get(command) : null
        if (c) {
            c.run(writer)
        } else {
            def output = new ConsoleOutput(writer)
            def shell = connection.shell
            def future = shell.evaluate(command, output)
            writer.println("===> ${future.get()}")
        }
    }

    void executeScript(String scriptFile, PrintWriter writer) {
        throw new UnsupportedOperationException()
    }

    boolean isFinished() {
        throw new UnsupportedOperationException()
    }
}