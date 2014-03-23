package br.com.tecsinapse.glimpse.jmx

import spock.lang.Specification

class JmxGlimpseTest extends Specification {

    def finder = Mock(GlimpseShellMXBeanFinder)
    def mxBean = Mock(GlimpseMXBean)
    def glimpse = new JmxGlimpse(mxBean, finder, null)

    def "create shell"() {
        setup:
        def id = "123"
        mxBean.createShell() >> id

        expect:
        glimpse.createShell() == id
    }

    def "get shell"() {
        setup:
        def id = "123"
        def shellMxBean = Mock(GlimpseShellMXBean)
        finder.find(id) >> shellMxBean

        expect:
        glimpse.getShell(id).getMxBean() == shellMxBean
    }

    def "destroy shell"() {
        setup:
        def id = "123"

        when:
        glimpse.destroyShell(id)

        then:
        1 * mxBean.destroyShell(id)
    }

}
