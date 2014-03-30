package br.com.tecsinapse.glimpse.spring

import br.com.tecsinapse.glimpse.DefaultGlimpse
import br.com.tecsinapse.glimpse.groovy.GroovyGlimpseShellFactory
import br.com.tecsinapse.glimpse.jmx.GlimpseMXBeanImpl
import org.springframework.beans.BeansException
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

import javax.management.ObjectName
import java.lang.management.ManagementFactory

class SpringGlimpse implements ApplicationContextAware, InitializingBean, DisposableBean {

    static final def OBJECT_NAME = new ObjectName("br.com.tecsinapse.glimpse:type=Glimpse")

    private ApplicationContext applicationContext

    private GlimpseMXBeanImpl glimpseMXBean;

    @Override
    void afterPropertiesSet() throws Exception {
        def propResolver = new SpringPropertyResolver()
        propResolver.setApplicationContext(applicationContext)
        def glimpse = new DefaultGlimpse(new GroovyGlimpseShellFactory(propResolver))
        glimpseMXBean = new GlimpseMXBeanImpl(glimpse)
        ManagementFactory.getPlatformMBeanServer().registerMBean(glimpseMXBean, OBJECT_NAME)
    }

    @Override
    void destroy() throws Exception {
        glimpseMXBean.destroyAllShells()
        ManagementFactory.getPlatformMBeanServer().unregisterMBean(OBJECT_NAME)
    }

    @Override
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext
    }
}
