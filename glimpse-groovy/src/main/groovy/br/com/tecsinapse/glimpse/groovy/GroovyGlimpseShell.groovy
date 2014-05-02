package br.com.tecsinapse.glimpse.groovy

import br.com.tecsinapse.glimpse.GlimpseShell
import br.com.tecsinapse.glimpse.Output
import org.codehaus.groovy.control.CompilerConfiguration

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class GroovyGlimpseShell implements GlimpseShell {

    private GroovyShell shell

    private Map<String, String> params = new HashMap<>()

    private ExecutorService executor = Executors.newFixedThreadPool(1)

    private PropertyResolver propertyResolver
    private MethodResolver methodResolver

    GroovyGlimpseShell(PropertyResolver propertyResolver, MethodResolver methodResolver) {
        this.propertyResolver = propertyResolver
        this.methodResolver = methodResolver
        shell = new GroovyShell(
                    new CompilerConfiguration(scriptBaseClass: "br.com.tecsinapse.glimpse.groovy.GlimpseScript"))
        shell.setProperty("params", params)
    }

    @Override
    void setParameter(String param, String value) {
        params.put(param, value)
    }

    @Override
    Future evaluate(String script, Output output) {
        return executor.submit({
            GlimpseScript s = (GlimpseScript) shell.parse(script)
            s.setOutput(output)
            s.setPropertyResolver(propertyResolver)
            s.setMethodResolver(methodResolver)
            return s.run()
        } as Callable)
    }
}
