package br.com.tecsinapse.glimpse.spring

import br.com.tecsinapse.glimpse.http.HttpClientHttpHandler
import br.com.tecsinapse.glimpse.http.HttpGlimpse
import br.com.tecsinapse.glimpse.jmx.GlimpseMXBean
import br.com.tecsinapse.glimpse.jmx.GlimpseShellEvaluationMXBean
import br.com.tecsinapse.glimpse.jmx.GlimpseShellMXBean
import org.springframework.context.support.ClassPathXmlApplicationContext
import spock.lang.Specification

import javax.management.JMX
import javax.management.ObjectName
import java.lang.management.ManagementFactory

class SpringGlimpseTest extends Specification {

    def "jmx"() {
        setup:
        def applicationContext = new ClassPathXmlApplicationContext("glimpseApplicationContext.xml")
        def mBeanServer = ManagementFactory.getPlatformMBeanServer()

        when:
        def glimpseObjectName = new ObjectName("br.com.tecsinapse.glimpse:type=Glimpse")
        def mxGlimpse = JMX.newMBeanProxy(mBeanServer, glimpseObjectName, GlimpseMXBean)
        def shellId = mxGlimpse.createShell()
        def shellObjectName = new ObjectName("br.com.tecsinapse.glimpse:type=Shell,id=${shellId}")
        def mxShell = JMX.newMBeanProxy(mBeanServer, shellObjectName, GlimpseShellMXBean)
        def evalId = mxShell.evaluate("testBean.hello")
        def evalObjectName = new ObjectName("br.com.tecsinapse.glimpse:type=Evaluation,shellId=${shellId},id=${evalId}")
        def mxEval = JMX.newMBeanProxy(mBeanServer, evalObjectName, GlimpseShellEvaluationMXBean)
        mxEval.run()
        while (!mxEval.finished) {
            Thread.yield()
        }
        def result = mxEval.result
        applicationContext.destroy()

        then:
        result == "hello"
        mBeanServer.queryNames(glimpseObjectName, null).isEmpty()
        mBeanServer.queryNames(shellObjectName, null).isEmpty()
        mBeanServer.queryNames(evalObjectName, null).isEmpty()
    }

    def "http"() {
        setup:
        System.setProperty("br.com.tecsinapse.glimpse.http.port", "9000")
        def applicationContext = new ClassPathXmlApplicationContext("glimpseApplicationContext.xml")
        def glimpse = new HttpGlimpse(new HttpClientHttpHandler("http://localhost:9000", null, null), 100)
        def shellId = glimpse.createShell()
        def shell = glimpse.getShell(shellId)

        when:
        def result = shell.evaluate("testBean.hello", null).get()

        then:
        result == "hello"

        cleanup:
        applicationContext.destroy()
    }

}
