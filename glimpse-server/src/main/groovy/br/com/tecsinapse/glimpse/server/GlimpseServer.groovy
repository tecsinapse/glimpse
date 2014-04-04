package br.com.tecsinapse.glimpse.server

import br.com.tecsinapse.glimpse.DefaultGlimpse
import br.com.tecsinapse.glimpse.groovy.GroovyGlimpseShellFactory
import br.com.tecsinapse.glimpse.groovy.PropertyResolver
import br.com.tecsinapse.glimpse.http.SunHttpGlimpseConnector
import br.com.tecsinapse.glimpse.jmx.GlimpseMXBeanImpl

import javax.management.ObjectName
import java.lang.management.ManagementFactory

class GlimpseServer {

    static final def OBJECT_NAME = new ObjectName("br.com.tecsinapse.glimpse:type=Glimpse")

    private GlimpseMXBeanImpl glimpseMXBean;
    private SunHttpGlimpseConnector httpConnector

    private PropertyResolver propertyResolver

    GlimpseServer(PropertyResolver propertyResolver) {
        this.propertyResolver = propertyResolver
    }

    def start() {
        def glimpse = new DefaultGlimpse(new GroovyGlimpseShellFactory(propertyResolver))
        glimpseMXBean = new GlimpseMXBeanImpl(glimpse)
        ManagementFactory.getPlatformMBeanServer().registerMBean(glimpseMXBean, OBJECT_NAME)
        def httpPort = System.getProperty("br.com.tecsinapse.glimpse.http.port")
        if (httpPort) {
            httpConnector = new SunHttpGlimpseConnector(glimpse, httpPort as int)
            httpConnector.start()
        }
    }

    def stop() {
        glimpseMXBean.destroyAllShells()
        ManagementFactory.getPlatformMBeanServer().unregisterMBean(OBJECT_NAME)
        if (httpConnector) {
            httpConnector.stop()
        }
    }

}
