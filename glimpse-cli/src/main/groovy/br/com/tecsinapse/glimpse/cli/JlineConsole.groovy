package br.com.tecsinapse.glimpse.cli

import jline.console.ConsoleReader

class JlineConsole implements Console {

    FileSystem fileSystem = new DefaultFileSystem()
    PrintWriter writer = new PrintWriter(System.out, true)

    @Override
    void start(Connection connection) {
        def consoleController = new ConsoleController(connection, fileSystem)
        def reader = new ConsoleReader()
        while (true) {
            def command = reader.readLine(consoleController.nextPrompt())
            consoleController.execute(command, writer)
            if (consoleController.isFinished()) {
                break
            }
        }
        reader.shutdown()
        System.exit(0)
    }

    @Override
    void runScript(Connection connection, String scriptFile) {
        def consoleController = new ConsoleController(connection, fileSystem)
        consoleController.executeScript(scriptFile, writer)
        System.exit(0)
    }

    @Override
    PrintWriter getWriter() {
        return writer;
    }
}
