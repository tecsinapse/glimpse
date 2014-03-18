package br.com.tecsinapse.glimpse.cli

public class ConsoleController {

    private Connection connection;

    ConsoleController(Connection connection) {
        this.connection = connection;
    }

    String nextPrompt() {
        throw new UnsupportedOperationException()
    }

    void execute(String command, PrintWriter writer) {
        throw new UnsupportedOperationException()
    }

    void executeScript(String scriptFile, PrintWriter writer) {
        throw new UnsupportedOperationException()
    }

    boolean isFinished() {
        throw new UnsupportedOperationException()
    }
}