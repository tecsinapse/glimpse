package br.com.tecsinapse.glimpse.groovy

abstract class GlimpseScript extends Script {

    @Override
    void print(o) {
        printStream.print(o)
    }

    @Override
    void println(o) {
        printStream.println(o)
    }

}
