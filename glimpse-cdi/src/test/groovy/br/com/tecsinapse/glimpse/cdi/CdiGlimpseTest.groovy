package br.com.tecsinapse.glimpse.cdi

import org.jboss.weld.environment.se.Weld
import spock.lang.Specification

class CdiGlimpseTest extends Specification {

    def "hello"() {
        setup:
        def weld = new Weld()
        def container = weld.initialize()
        def cdiGlimpse = container.instance().select(CdiGlimpse).get()

        expect:
        cdiGlimpse.hello() == "hello"
    }

}
