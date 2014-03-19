package br.com.tecsinapse.glimpse.jmx

import br.com.tecsinapse.glimpse.Glimpse
import br.com.tecsinapse.glimpse.GlimpseShell
import spock.lang.Specification

class GlimpseMXBeanImplTest extends Specification {

    def glimpse = Mock(Glimpse.class)
    def mxBean = new GlimpseMXBeanImpl(glimpse)

    def "create shell"() {
        setup:
        def id = "id"
        glimpse.createShell() >> id

        expect:
        mxBean.createShell() == id
    }

    def "set parameter"() {
        setup:
        def id = "id"
        glimpse.createShell() >> id
        def shell = Mock(GlimpseShell.class)
        glimpse.getShell(id) >> shell
        def param = "param"
        def value = "value"

        when:
        mxBean.setParameter(id, param, value)

        then:
        1 * shell.setParameter(param, value)
    }

    def "destroy shell"() {
        setup:
        def id = "id"

        when:
        mxBean.destroyShell(id)

        then:
        1 * glimpse.destroyShell(id)
    }

}
