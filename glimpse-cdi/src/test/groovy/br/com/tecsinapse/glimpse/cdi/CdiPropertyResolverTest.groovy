package br.com.tecsinapse.glimpse.cdi

import br.com.tecsinapse.glimpse.DefaultGlimpse
import br.com.tecsinapse.glimpse.Output
import br.com.tecsinapse.glimpse.groovy.GroovyGlimpseShellFactory
import org.jboss.weld.environment.se.Weld
import spock.lang.Specification

class CdiPropertyResolverTest extends Specification {

    def weld = new Weld()
    def glimpse = new DefaultGlimpse(new GroovyGlimpseShellFactory(new CdiPropertyResolver()))
    def shellId = glimpse.createShell()
    def shell = glimpse.getShell(shellId)

    def setup() {
        weld.initialize()
    }

    def "get property"() {
        when:
        def result = shell.evaluate("helloBean.hello()", null).get()

        then:
        result == "hello"
    }

    def "missing property"() {
        setup:
        def builder = new StringBuilder()
        def output = [

                print: { o ->
                    builder.append(o)
                },

                println: { o ->
                    builder.append(o)
                    builder.append("\n")
                }

        ] as Output

        when:
        def result = shell.evaluate("missingBean.hello()", output).get()

        then:
        builder.toString().contains("Could not find beans for Type=class java.lang.Object and name:missingBean")
        result == null
    }

}
