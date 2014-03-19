package br.com.tecsinapse.glimpse.jmx

import br.com.tecsinapse.glimpse.GlimpseShell
import spock.lang.Specification

import javax.management.AttributeChangeNotification
import javax.management.JMX
import javax.management.NotificationListener
import javax.management.ObjectName
import java.lang.management.ManagementFactory
import java.util.concurrent.Future

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
        def future = Mock(Future)
        future.isDone() >> false
        shell.evaluate(script, _) >> future
        def notification = null
        mBeanServer.addNotificationListener(objectName, { n, hb ->
            notification = n
        } as NotificationListener, null, null)

        when:
        mxBeanProxy.evaluate(script)

        then:
        mxBeanProxy.evaluating
        notification instanceof AttributeChangeNotification
        notification.attributeName == "evaluating"
        notification.oldValue == false
        notification.newValue == true
    }

}
