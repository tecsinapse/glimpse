package br.com.tecsinapse.glimpse.groovy

import br.com.tecsinapse.glimpse.Output

abstract class GlimpseScript extends Script {

    private Output output

    private PropertyResolver propertyResolver

    void setOutput(Output output) {
        this.output = output
    }

    void setPropertyResolver(PropertyResolver propertyResolver) {
        this.propertyResolver = propertyResolver
    }

    @Override
    void print(o) {
        output.print(o)
    }

    @Override
    void println(o) {
        output.println(o)
    }

    def propertyMissing(String name) {
        return propertyResolver.getProperty(name)
    }

}
