package br.com.tecsinapse.glimpse.http

import br.com.tecsinapse.glimpse.Output

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque

class JsonOutput implements Output {

    private BlockingQueue<String> printQueue = new LinkedBlockingDeque<>()

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
        printQueue.add(o.toString())
    }

    @Override
    void println(Object o) {
        print("${o}\n")
    }

    def getNextOutput() {
        def output = [:]
        def strings = []
        printQueue.drainTo(strings)
        def print = strings.join("")
        if (print) {
            output.print = print
        }
        return output
    }
}
