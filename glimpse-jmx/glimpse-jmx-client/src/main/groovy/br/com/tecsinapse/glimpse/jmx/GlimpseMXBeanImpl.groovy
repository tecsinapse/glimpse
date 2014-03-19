package br.com.tecsinapse.glimpse.jmx

import br.com.tecsinapse.glimpse.Glimpse

import javax.management.ObjectName
import java.lang.management.ManagementFactory

class GlimpseMXBeanImpl implements GlimpseMXBean {

    private Glimpse glimpse

    GlimpseMXBeanImpl(Glimpse glimpse) {
        this.glimpse = glimpse
    }

    @Override
    String createShell() {
        def id = glimpse.createShell()
        def shell = glimpse.getShell(id)
        def shellMx = new GlimpseShellMXBeanImpl(id, shell)
        def objectName = new ObjectName("br.com.tecsinapse.glimpse:type=Shell,id=${id}")
        ManagementFactory.getPlatformMBeanServer().registerMBean(shellMx, objectName)
        return id
    }

    @Override
    void destroyShell(String shellId) {
        glimpse.destroyShell(shellId)
        def objectName = new ObjectName("br.com.tecsinapse.glimpse:type=Shell,id=${shellId}")
        ManagementFactory.getPlatformMBeanServer().unregisterMBean(objectName)
    }
}
