package br.com.tecsinapse.glimpse.cli

import br.com.tecsinapse.glimpse.Output

class ConsoleOutput implements Output {

    private PrintWriter writer

    ConsoleOutput(PrintWriter writer) {
        this.writer = writer
    }

    @Override
    void enableProgress(long totalSteps) {
        throw new UnsupportedOperationException()
    }

    @Override
    void updateProgress(long workedSteps) {
        throw new UnsupportedOperationException()
    }

    @Override
    void print(Object o) {
        writer.print(o)
    }

    @Override
    void println(Object o) {
        writer.println(o)
    }
}
