package br.com.tecsinapse.glimpse.jmx

import br.com.tecsinapse.glimpse.GlimpseShell
import br.com.tecsinapse.glimpse.groovy.GroovyGlimpseShell
import br.com.tecsinapse.glimpse.groovy.PropertyResolver
import spock.lang.Specification
import spock.lang.Timeout

import javax.management.AttributeChangeNotification
import javax.management.InstanceNotFoundException
import javax.management.JMX
import javax.management.Notification
import javax.management.NotificationListener
import javax.management.ObjectName
import java.lang.management.ManagementFactory
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

class GlimpseShellMXBeanImplTest extends Specification {

    def id = "123"
    def propertyResolver = Mock(PropertyResolver.class)
    def shell = new GroovyGlimpseShell(propertyResolver)
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
        def result = shell.evaluate("params.param", null).get()

        then:
        value == result
    }

    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    def "evaluate"() {
        setup:
        def script = "1 + 1"

        when:
        def evalId = mxBeanProxy.evaluate(script)
        def objectName = new ObjectName("br.com.tecsinapse.glimpse:type=Evaluation,id=${evalId},shellId=${id}")
        def evalProxy = JMX.newMBeanProxy(mBeanServer, objectName, GlimpseShellEvaluationMXBean.class)
        while (!evalProxy.finished) {
            sleep(100)
        }
        def proxyId = evalProxy.id
        def proxyScript = evalProxy.script
        def proxyResult = evalProxy.result
        mxBeanProxy.destroyEvaluation(evalId)
        def objectInstance = null
        try {
           objectInstance = mBeanServer.getObjectInstance(objectName)
        } catch (InstanceNotFoundException e) {
        }

        then:
        evalId == proxyId
        script == proxyScript
        "2" == proxyResult
        objectInstance == null
    }

}
