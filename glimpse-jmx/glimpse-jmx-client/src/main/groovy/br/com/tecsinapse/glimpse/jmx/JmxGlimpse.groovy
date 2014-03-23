package br.com.tecsinapse.glimpse.jmx

import br.com.tecsinapse.glimpse.Glimpse
import br.com.tecsinapse.glimpse.GlimpseShell

class JmxGlimpse implements Glimpse {

    private GlimpseShellMXBeanFinder glimpseShellMXBeanFinder
    private GlimpseShellEvaluationMXBeanFinder evaluationMXBeanFinder
    private GlimpseMXBean mxBean

    JmxGlimpse(GlimpseMXBean mxBean, GlimpseShellMXBeanFinder glimpseShellMXBeanFinder, GlimpseShellEvaluationMXBeanFinder evaluationMXBeanFinder) {
        this.mxBean = mxBean
        this.glimpseShellMXBeanFinder = glimpseShellMXBeanFinder
        this.evaluationMXBeanFinder = evaluationMXBeanFinder
    }

    @Override
    String createShell() {
        return mxBean.createShell()
    }

    @Override
    GlimpseShell getShell(String id) {
        return new JmxGlimpseShell(glimpseShellMXBeanFinder.find(id), evaluationMXBeanFinder)
    }

    @Override
    void destroyShell(String id) {
        mxBean.destroyShell(id)
    }
}
