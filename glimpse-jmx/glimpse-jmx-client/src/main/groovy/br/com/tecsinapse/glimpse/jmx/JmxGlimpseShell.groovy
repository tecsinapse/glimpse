package br.com.tecsinapse.glimpse.jmx

import br.com.tecsinapse.glimpse.GlimpseShell
import br.com.tecsinapse.glimpse.Output

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class JmxGlimpseShell implements GlimpseShell {

    private String id

    private GlimpseShellMXBean mxBean

    private GlimpseShellEvaluationMXBeanFinder evaluationMXBeanFinder

    private ExecutorService executor = Executors.newFixedThreadPool(1)

    JmxGlimpseShell(GlimpseShellMXBean mxBean, GlimpseShellEvaluationMXBeanFinder finder) {
        this.mxBean = mxBean
        this.id = mxBean.getId()
        this.evaluationMXBeanFinder = finder
    }

    GlimpseShellMXBean getMxBean() {
        return mxBean
    }

    @Override
    void setParameter(String param, String value) {
        mxBean.setParameter(param, value)
    }

    @Override
    Future<String> evaluate(String script, Output output) {
        return executor.submit({
            def evalId = mxBean.evaluate(script)
            def evalMXBean = evaluationMXBeanFinder.find(id, evalId, output)
            evalMXBean.run()
            while (!evalMXBean.finished) {
                Thread.yield()
            }
            String result = evalMXBean.result
            mxBean.destroyEvaluation(evalId)
            return result
        } as Callable)
    }
}
