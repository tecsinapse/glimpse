package br.com.tecsinapse.glimpse.jmx

import br.com.tecsinapse.glimpse.GlimpseShell

class GlimpseShellMXBeanImpl implements GlimpseShellMXBean {

    private String id

    private GlimpseShell shell

    GlimpseShellMXBeanImpl(String id, GlimpseShell shell) {
        this.id = id
        this.shell = shell
    }

    @Override
    String getId() {
        return id
    }

    @Override
    void setParameter(String param, String value) {
        throw new UnsupportedOperationException()
    }

    @Override
    void evaluate(String script) {
        throw new UnsupportedOperationException()
    }

    @Override
    boolean isEvaluating() {
        throw new UnsupportedOperationException()
    }

    @Override
    boolean isProgressEnabled() {
        throw new UnsupportedOperationException()
    }

    @Override
    int getTotalSteps() {
        throw new UnsupportedOperationException()
    }

    @Override
    int getWorkedSteps() {
        throw new UnsupportedOperationException()
    }

    @Override
    boolean isOutputChanged() {
        throw new UnsupportedOperationException()
    }

    @Override
    boolean isFinished() {
        throw new UnsupportedOperationException()
    }

    @Override
    String getOutputSinceLastChange() {
        throw new UnsupportedOperationException()
    }
}
