package br.com.tecsinapse.glimpse.jmx

import br.com.tecsinapse.glimpse.Glimpse
import br.com.tecsinapse.glimpse.GlimpseShell

class GlimpseMXBeanImpl implements GlimpseMXBean {

    private Glimpse glimpse

    GlimpseMXBeanImpl(Glimpse glimpse) {
        this.glimpse = glimpse
    }

    @Override
    String createShell() {
        return glimpse.createShell()
    }

    @Override
    void setParameter(String shellId, String param, String value) {
        GlimpseShell shell = glimpse.getShell(shellId)
        shell.setParameter(param, value)
    }

    @Override
    void destroyShell(String shellId) {
        glimpse.destroyShell(shellId)
    }
}
