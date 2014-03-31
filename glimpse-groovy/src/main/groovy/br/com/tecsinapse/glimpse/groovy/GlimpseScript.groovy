package br.com.tecsinapse.glimpse.groovy

import br.com.tecsinapse.glimpse.Output

abstract class GlimpseScript extends Script {

    private Output output

    private PropertyResolver propertyResolver

    private MethodResolver methodResolver

    void setOutput(Output output) {
        this.output = output
    }

    void setPropertyResolver(PropertyResolver propertyResolver) {
        this.propertyResolver = propertyResolver
    }

    void setMethodResolver(MethodResolver methodResolver) {
        this.methodResolver = methodResolver
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

    def methodMissing(String name, def args) {
        return methodResolver.methodMissing(name, args)
    }

}
