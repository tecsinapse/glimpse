package br.com.tecsinapse.glimpse.jmx

import javax.management.JMX
import javax.management.ObjectName
import javax.management.remote.JMXConnectorFactory
import javax.management.remote.JMXServiceURL

class JmxGlimpseFactory {

    static JmxGlimpse create(String serviceUrl) {
        def jmxServiceUrl = new JMXServiceURL(serviceUrl)
        def connector = JMXConnectorFactory.connect(jmxServiceUrl)
        def connection = connector.getMBeanServerConnection()
        def objectName = new ObjectName("br.com.tecsinapse.glimpse:type=Glimpse")
        def mxBean = JMX.newMBeanProxy(connection, objectName, GlimpseMXBean)
        return new JmxGlimpse(mxBean, new DefaultGlimpseShellMXBeanFinder(connection), new DefaultGlimpseShellEvaluationMXBeanFinder(connection))
    }

}
