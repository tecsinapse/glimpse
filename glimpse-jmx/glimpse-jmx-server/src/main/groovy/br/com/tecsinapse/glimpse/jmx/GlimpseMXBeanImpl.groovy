package br.com.tecsinapse.glimpse.jmx

import br.com.tecsinapse.glimpse.Glimpse

import javax.management.ObjectName
import java.lang.management.ManagementFactory

class GlimpseMXBeanImpl implements GlimpseMXBean {

    private Glimpse glimpse

    private Map<String, GlimpseShellMXBeanImpl> shells = [:]

    GlimpseMXBeanImpl(Glimpse glimpse) {
        this.glimpse = glimpse
    }

    @Override
    String createShell() {
        def id = glimpse.createShell()
        def shell = glimpse.getShell(id)
        def shellMx = new GlimpseShellMXBeanImpl(id, shell)
        def objectName = createShellObjectName(id)
        ManagementFactory.getPlatformMBeanServer().registerMBean(shellMx, objectName)
        shells.put(id, shellMx)
        return id
    }

    private ObjectName createShellObjectName(id) {
        new ObjectName("br.com.tecsinapse.glimpse:type=Shell,id=${id}")
    }

    @Override
    void destroyShell(String shellId) {
        glimpse.destroyShell(shellId)
        def objectName = createShellObjectName(shellId)
        def shellMx = shells.get(shellId)
        shellMx.destroyAllEvaluations()
        ManagementFactory.getPlatformMBeanServer().unregisterMBean(objectName)
        shells.remove(shellId)
    }

    void destroyAllShells() {
        shells.keySet().toArray().each {
            destroyShell(it)
        }
    }
}
