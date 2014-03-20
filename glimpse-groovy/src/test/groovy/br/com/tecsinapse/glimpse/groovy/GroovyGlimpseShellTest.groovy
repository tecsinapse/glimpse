package br.com.tecsinapse.glimpse.groovy

import br.com.tecsinapse.glimpse.Output
import spock.lang.Specification

class GroovyGlimpseShellTest extends Specification {

    def propertyResolver = Mock(PropertyResolver.class)
    def shell = new GroovyGlimpseShell(propertyResolver)

    def "evaluation"() {
        expect:
        shell.evaluate("1 + 1", null).get() == 2
    }

    def "output stream redirection"() {
        setup:
        def output = Mock(Output.class)
        def message = "test"

        when:
        shell.evaluate("println '${message}'", output).get()

        then:
        1 * output.println(message)
    }

    def "parameters"() {
        setup:
        def param = "param1"
        def value = "value1"
        shell.setParameter(param, value)

        expect:
        shell.evaluate("params.${param}", null).get() == value
    }

    def "exception"() {
        setup:
        def output = Mock(Output.class)
        output.println(_)

        expect:
        shell.evaluate("throw new UnsupportedOperationException", output).get() == null
    }

    def "property resolver"() {
        setup:
        def property = "object"
        def object = [:]
        propertyResolver.getProperty(property) >> object

        when:
        shell.evaluate("object.prop = 'value'", null).get()

        then:
        "value" == object.prop
    }

}
