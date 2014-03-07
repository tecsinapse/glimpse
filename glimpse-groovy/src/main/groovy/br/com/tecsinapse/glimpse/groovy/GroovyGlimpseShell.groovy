package br.com.tecsinapse.glimpse.groovy

import br.com.tecsinapse.glimpse.GlimpseShell
import org.codehaus.groovy.control.CompilerConfiguration

class GroovyGlimpseShell implements GlimpseShell {

    private GroovyShell shell

    private Map<String, String> params = new HashMap<>()

    GroovyGlimpseShell() {
        shell = new GroovyShell(
                    new CompilerConfiguration(scriptBaseClass: "br.com.tecsinapse.glimpse.groovy.GlimpseScript"))
        shell.setProperty("params", params)
    }

    @Override
    void setParameter(String param, String value) {
        params.put(param, value)
    }

    @Override
    void setOutputStream(PrintStream out) {
        shell.setVariable("printStream", out)
    }

    @Override
    Object evaluate(String script) {
        return shell.evaluate(script)
    }
}
