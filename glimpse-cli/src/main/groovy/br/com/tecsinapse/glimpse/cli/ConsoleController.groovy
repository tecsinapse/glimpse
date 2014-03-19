package br.com.tecsinapse.glimpse.cli

public class ConsoleController {

    private boolean finished = false
    private Connection connection
    private FileSystem fileSystem
    private Command lastCommand
    Map<String, Command> commands = [
            "\\quit": [
                run: { w ->
                    finished = true
                    return null
                }
            ] as Command
    ]


    ConsoleController(Connection connection, FileSystem fileSystem) {
        this.connection = connection;
        this.fileSystem = fileSystem
    }

    String nextPrompt() {
        return "${connection.description}> "
    }

    void execute(String command, PrintWriter writer) {
        if (lastCommand) {
            lastCommand = lastCommand.answer(command, writer)
        } else {
            def c = command.startsWith("\\") ? commands.get(command) : null
            if (c) {
                lastCommand = c.run(writer)
            } else {
                def output = new ConsoleOutput(writer)
                def shell = connection.shell
                def future = shell.evaluate(command, output)
                writer.println("===> ${future.get()}")
            }
        }
    }

    void executeScript(String scriptFile, PrintWriter writer) {
        def script = fileSystem.read(scriptFile)
        def shell = connection.shell
        writer.println("Executing script at: ${connection.description}")
        writer.println("----------------------------------------------")
        writer.println()
        def future = shell.evaluate(script, new ConsoleOutput(writer))
        writer.println("===> ${future.get()}")

    }

    boolean isFinished() {
        finished
    }
}