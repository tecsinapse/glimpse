package br.com.tecsinapse.glimpse.jmx

import br.com.tecsinapse.glimpse.GlimpseShell
import spock.lang.Specification

import javax.management.JMX
import javax.management.ObjectName
import java.lang.management.ManagementFactory

class GlimpseShellMXBeanImplTest extends Specification {

    def id = "123"
    def shell = Mock(GlimpseShell.class)
    def mxBean = new GlimpseShellMXBeanImpl(id, shell)
    def objectName = new ObjectName("br.com.tecsinapse.glimpse:type=Shell,id=${id}")
    GlimpseShellMXBean mxBeanProxy = null
    def mBeanServer = ManagementFactory.getPlatformMBeanServer()

    def setup() {
        mBeanServer.registerMBean(mxBean, objectName)
        mxBeanProxy = JMX.newMBeanProxy(mBeanServer, objectName, GlimpseShellMXBean.class)
    }

    def cleanup() {
        mBeanServer.unregisterMBean(objectName)
    }

    def "set parameter"() {
        setup:
        def param = "param"
        def value = "value"

        when:
        mxBeanProxy.setParameter(param, value)

        then:
        1 * shell.setParameter(param, value)
    }

    def "evaluate"() {
        setup:
        def script = "script"

        when:
        def evalId = mxBeanProxy.evaluate(script)
        def objectName = new ObjectName("br.com.tecsinapse.glimpse:type=Evaluation,id=${evalId},shellId=${id}")
        def evalProxy = JMX.newMBeanProxy(mBeanServer, objectName, GlimpseShellEvaluationMXBean.class)

        def proxyId = evalProxy.id
        def proxyScript = evalProxy.script
        mxBeanProxy.destroyEvaluation(evalId)
        def objectInstance = null
        try {
           objectInstance = mBeanServer.getObjectInstance(objectName)
        } catch (InstanceNotFoundException) {
        }

        then:
        evalId == proxyId
        script == proxyScript
        objectInstance == null
    }

}
