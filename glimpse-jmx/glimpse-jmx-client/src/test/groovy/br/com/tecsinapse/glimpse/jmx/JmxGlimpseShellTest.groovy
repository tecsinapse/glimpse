package br.com.tecsinapse.glimpse.jmx

import br.com.tecsinapse.glimpse.Output
import spock.lang.Specification

class JmxGlimpseShellTest extends Specification {

    def id = "123"
    def finder = Mock(GlimpseShellEvaluationMXBeanFinder)
    def mxBean = Mock(GlimpseShellMXBean.class)
    def shell

    def setup() {
        mxBean.getId() >> id
        shell = new JmxGlimpseShell(mxBean, finder)
    }

    def "set parameter"() {
        setup:
        def param = "param"
        def value = "value"

        when:
        shell.setParameter(param, value)

        then:
        1 * mxBean.setParameter(param, value)
    }

    def "evaluate"() {
        setup:
        def evalId = "456"
        def script = "script"
        mxBean.evaluate(script) >> evalId
        def output = Mock(Output)
        def evalMxBean = Mock(GlimpseShellEvaluationMXBean)
        evalMxBean.finished >> true
        evalMxBean.result >> "1"
        finder.find(id, evalId, output) >> evalMxBean

        when:
        def result = shell.evaluate(script, output).get()

        then:
        result == "1"
        1 * evalMxBean.run()
    }

}
