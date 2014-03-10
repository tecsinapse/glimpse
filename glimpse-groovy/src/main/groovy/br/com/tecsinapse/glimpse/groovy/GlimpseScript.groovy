package br.com.tecsinapse.glimpse.groovy

import br.com.tecsinapse.glimpse.Output

abstract class GlimpseScript extends Script {

    private Output output

    void setOutput(Output output) {
        this.output = output
    }

    @Override
    void print(o) {
        output.print(o)
    }

    @Override
    void println(o) {
        output.println(o)
    }

}
