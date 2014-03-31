package br.com.tecsinapse.glimpse.jmx

import br.com.tecsinapse.glimpse.groovy.GroovyGlimpseShell
import spock.lang.Specification
import spock.lang.Timeout

import javax.management.*
import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit

class GlimpseShellEvaluationMXBeanImplTest extends Specification {

    def id = "123"
    def shellId = "123"
    def shell = new GroovyGlimpseShell(null, null)
    def script =
"""
println 'test1'
sleep(1000)
println 'test2'
return 1
"""
    def objectName = new ObjectName("br.com.tecsinapse.glimpse:type=Evaluation,id=${id},shellId=${shellId}")
    def mxBean = new GlimpseShellEvaluationMXBeanImpl(id, script, shell)
    GlimpseShellEvaluationMXBean mBeanProxy = null
    def mBeanServer = ManagementFactory.getPlatformMBeanServer()

    def setup() {
        mBeanServer.registerMBean(mxBean, objectName)
        mBeanProxy = JMX.newMBeanProxy(mBeanServer, objectName, GlimpseShellEvaluationMXBean.class)
    }

    def cleanup() {
        mBeanServer.unregisterMBean(objectName)
    }

    def "before run"() {
        expect:
        !mBeanProxy.running
        !mBeanProxy.finished
        !mBeanProxy.progressEnabled
        !mBeanProxy.outputChanged
    }

    def "before output since last change"() {
        when:
        mBeanProxy.getOutputSinceLastChange()

        then:
        thrown(IllegalStateException)
    }

    def "before run total steps"() {
        when:
        mBeanProxy.getTotalSteps()

        then:
        thrown(IllegalStateException)
    }

    def "before run worked steps"() {
        when:
        mBeanProxy.getWorkedSteps()

        then:
        thrown(IllegalStateException)
    }

    def "before run result"() {
        when:
        mBeanProxy.getResult()

        then:
        thrown(IllegalStateException)
    }

    def "running"() {
        when:
        mBeanProxy.run()

        then:
        mBeanProxy.running
        !mBeanProxy.finished
    }

    def "running result"() {
        when:
        mBeanProxy.run()
        mBeanProxy.getResult()

        then:
        thrown(IllegalStateException)
    }

    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    def "finished"() {
        setup:
        def notifications = []
        def outputs = []
        mBeanServer.addNotificationListener(objectName, { Notification n, hb ->
            def an = (AttributeChangeNotification) n
            notifications << [an.attributeName, an.newValue]
            if (an.attributeName == "outputChanged" && an.newValue) {
                def o = mBeanProxy.getOutputSinceLastChange()
                if (o) {
                    outputs << o
                }
            }
        } as NotificationListener, null, null)

        when:
        mBeanProxy.run()
        while (!mBeanProxy.finished) {
            sleep(100)
        }
        if (mBeanProxy.outputChanged) {
            def o = mBeanProxy.getOutputSinceLastChange()
            if (o) {
                outputs << o
            }
        }

        then:
        !mBeanProxy.running
        mBeanProxy.finished
        !mBeanProxy.outputChanged
        outputs == ["test1\n", "test2\n"]
        notifications ==
         [["running", true],
         ["outputChanged", true],
         ["outputChanged", false],
         ["outputChanged", true],
         ["outputChanged", false],
         ["running", false],
         ["finished", true]]
    }

}
