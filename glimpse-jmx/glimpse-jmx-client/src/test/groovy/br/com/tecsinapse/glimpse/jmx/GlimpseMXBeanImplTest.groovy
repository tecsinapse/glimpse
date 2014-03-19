package br.com.tecsinapse.glimpse.jmx

import br.com.tecsinapse.glimpse.Glimpse
import spock.lang.Specification

import javax.management.InstanceNotFoundException
import javax.management.JMX
import javax.management.ObjectName
import java.lang.management.ManagementFactory

class GlimpseMXBeanImplTest extends Specification {

    def glimpse = Mock(Glimpse.class)
    def mxBean = new GlimpseMXBeanImpl(glimpse)

    def "create shell"() {
        setup:
        def id = "123"
        glimpse.createShell() >> id

        when:
        mxBean.createShell()

        then:
        def objectName = new ObjectName("br.com.tecsinapse.glimpse:type=Shell,id=${id}")
        def shellMX = JMX.newMBeanProxy(ManagementFactory.getPlatformMBeanServer(), objectName, GlimpseShellMXBean.class)
        id == shellMX.id
        ManagementFactory.getPlatformMBeanServer().unregisterMBean(objectName)
    }

    def "destroy shell"() {
        setup:
        def id = "id"
        def objectName = new ObjectName("br.com.tecsinapse.glimpse:type=Shell,id=${id}")
        glimpse.createShell() >> id
        mxBean.createShell()
        def found = ManagementFactory.getPlatformMBeanServer().getMBeanInfo(objectName) != null

        when:
        mxBean.destroyShell(id)
        ManagementFactory.getPlatformMBeanServer().getMBeanInfo(objectName)

        then:
        found
        1 * glimpse.destroyShell(id)
        thrown(InstanceNotFoundException)
    }

}
