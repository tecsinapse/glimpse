package br.com.tecsinapse.glimpse.spring

import br.com.tecsinapse.glimpse.server.GlimpseServer
import org.springframework.beans.BeansException
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

import javax.management.ObjectName

class SpringGlimpse implements ApplicationContextAware, InitializingBean, DisposableBean {

    static final def OBJECT_NAME = new ObjectName("br.com.tecsinapse.glimpse:type=Glimpse")

    private ApplicationContext applicationContext

    private GlimpseServer glimpseServer

    @Override
    void afterPropertiesSet() throws Exception {
        def propResolver = new SpringPropertyResolver()
        propResolver.setApplicationContext(applicationContext)
        glimpseServer = new GlimpseServer(propResolver)
        glimpseServer.start()
    }

    @Override
    void destroy() throws Exception {
        glimpseServer.stop()
    }

    @Override
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext
    }
}
