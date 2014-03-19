package br.com.tecsinapse.glimpse.cli

import jline.console.ConsoleReader

class JlineConsole implements Console {

    PrintWriter writer = new PrintWriter(System.out, true)

    @Override
    void start(Connection connection) {
        def consoleController = new ConsoleController(connection)
        def reader = new ConsoleReader()
        while (true) {
            def command = reader.readLine(consoleController.nextPrompt())
            consoleController.execute(command, writer)
            if (consoleController.isFinished()) {
                break
            }
        }
        reader.shutdown()
    }

    @Override
    void runScript(Connection connection, String scriptFile) {
        def consoleController = new ConsoleController(connection)
        consoleController.executeScript(scriptFile, writer)
    }

    @Override
    PrintWriter getWriter() {
        return writer;
    }
}
