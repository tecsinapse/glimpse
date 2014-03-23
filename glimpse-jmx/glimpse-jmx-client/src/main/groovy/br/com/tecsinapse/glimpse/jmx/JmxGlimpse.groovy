package br.com.tecsinapse.glimpse.jmx

import br.com.tecsinapse.glimpse.Glimpse
import br.com.tecsinapse.glimpse.GlimpseShell

class JmxGlimpse implements Glimpse {

    private GlimpseShellMXBeanFinder glimpseShellMXBeanFinder
    private GlimpseMXBean mxBean

    JmxGlimpse(GlimpseShellMXBeanFinder glimpseShellMXBeanFinder, GlimpseMXBean mxBean) {
        this.mxBean = mxBean
        this.glimpseShellMXBeanFinder = glimpseShellMXBeanFinder
    }

    @Override
    String createShell() {
        return mxBean.createShell()
    }

    @Override
    GlimpseShell getShell(String id) {
        return new JmxGlimpseShell(glimpseShellMXBeanFinder.find(id))
    }

    @Override
    void destroyShell(String id) {
        mxBean.destroyShell(id)
    }
}
