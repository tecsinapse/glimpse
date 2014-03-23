package br.com.tecsinapse.glimpse.jmx

import javax.management.JMX
import javax.management.MBeanServerConnection
import javax.management.ObjectName

class DefaultGlimpseShellMXBeanFinder implements GlimpseShellMXBeanFinder {

    private MBeanServerConnection mBeanServerConnection

    DefaultGlimpseShellMXBeanFinder(MBeanServerConnection mBeanServerConnection) {
        this.mBeanServerConnection = mBeanServerConnection
    }

    @Override
    GlimpseShellMXBean find(String id) {
        def objectName = new ObjectName("br.com.tecsinapse.glimpse:type=Shell,id=${id}")
        return JMX.newMBeanProxy(mBeanServerConnection, objectName, GlimpseShellMXBeanFinder)
    }
}
