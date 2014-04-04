package br.com.tecsinapse.glimpse.cdi

import br.com.tecsinapse.glimpse.server.GlimpseServer

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import javax.ejb.Startup

@javax.ejb.Singleton
@Startup
class CdiGlimpse {

    def glimpseServer = new GlimpseServer(new CdiPropertyResolver())

    @PostConstruct
    void start() {
        glimpseServer.start()
    }

    @PreDestroy
    void stop() {
        glimpseServer.stop()
        println("glimpse stopped")
    }

}
