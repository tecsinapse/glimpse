package br.com.tecsinapse.glimpse.jmx

import br.com.tecsinapse.glimpse.GlimpseShell

import javax.management.AttributeChangeNotification
import javax.management.NotificationBroadcasterSupport
import java.util.concurrent.Executors
import java.util.concurrent.Future

class GlimpseShellMXBeanImpl extends NotificationBroadcasterSupport implements GlimpseShellMXBean {

    private String id

    private GlimpseShell shell

    private Future future

    private long sequenceNumber = 1;

    def executor = Executors.newFixedThreadPool(1)

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
        shell.setParameter(param, value)
    }

    @Override
    void evaluate(String script) {
        future = shell.evaluate(script, null)
        sendNotification(new AttributeChangeNotification(this, sequenceNumber++, System.currentTimeMillis(), "Evaluating changed", "evaluating", "java.lang.String", false, true))
        executor.submit({
            while (!future.done) {
                Thread.yield()
            }
            future = null
        } as Runnable)
    }

    @Override
    boolean isEvaluating() {
        future != null
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
        return future == null
    }

    @Override
    String getOutputSinceLastChange() {
        throw new UnsupportedOperationException()
    }

    String getResult() {
        throw new UnsupportedOperationException()
    }
}
