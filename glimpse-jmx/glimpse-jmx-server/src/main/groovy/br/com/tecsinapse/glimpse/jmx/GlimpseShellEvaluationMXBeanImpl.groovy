package br.com.tecsinapse.glimpse.jmx

import br.com.tecsinapse.glimpse.GlimpseShell

import java.util.concurrent.Future

class GlimpseShellEvaluationMXBeanImpl implements GlimpseShellEvaluationMXBean {

    private GlimpseShell glimpseShell

    private String id

    private String script

    private Future future

    GlimpseShellEvaluationMXBeanImpl(String id, String script, GlimpseShell glimpseShell) {
        this.id = id
        this.script = script
        this.glimpseShell = glimpseShell
    }

    @Override
    String getId() {
        return id
    }

    @Override
    String getScript() {
        return script
    }

    @Override
    boolean isFinished() {
        return future != null && future.isDone()
    }

    @Override
    String getResult() {
        return future.get()
    }

    def run() {
        future = glimpseShell.evaluate(script, null)
    }

}
