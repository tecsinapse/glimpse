package br.com.tecsinapse.glimpse.groovy

import spock.lang.Specification

class GroovyGlimpseShellTest extends Specification {

    def shell = new GroovyGlimpseShell()

    def "evaluation"() {
        expect:
        shell.evaluate("1 + 1") == 2
    }

    def "output stream redirection"() {
        setup:
        def output = new ByteArrayOutputStream()
        def message = "test"
        shell.setOutputStream(new PrintStream(output))

        when:
        shell.evaluate("println '${message}'")

        then:
        output.toByteArray() == "${message}\n".getBytes()
    }

    def "parameters"() {
        setup:
        def param = "param1"
        def value = "value1"
        shell.setParameter(param, value)

        expect:
        shell.evaluate("params.${param}") == value
    }

}
