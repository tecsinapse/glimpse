package br.com.tecsinapse.glimpse.cdi

import br.com.tecsinapse.glimpse.DefaultGlimpse
import br.com.tecsinapse.glimpse.groovy.GroovyGlimpseShellFactory
import org.jboss.weld.environment.se.Weld
import spock.lang.Specification

import java.util.concurrent.ExecutionException

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
        when:
        shell.evaluate("missingBean.hello()", null).get()

        then:
        ExecutionException e = thrown()
        e.cause instanceof IllegalStateException
        e.cause.message == 'Could not find beans for Type=class java.lang.Object and name:missingBean'
    }

}
