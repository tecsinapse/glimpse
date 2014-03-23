package br.com.tecsinapse.glimpse.jmx

import br.com.tecsinapse.glimpse.GlimpseShell
import br.com.tecsinapse.glimpse.Output

import javax.management.AttributeChangeNotification
import javax.management.Notification
import javax.management.NotificationBroadcasterSupport
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class GlimpseShellEvaluationMXBeanImpl extends NotificationBroadcasterSupport implements GlimpseShellEvaluationMXBean {

    private GlimpseShell glimpseShell

    private String id

    private String script

    private Future future

    private boolean progressEnabled

    private long sequence = 1

    private class EvalOutput implements  Output {

        private Queue<String> queue = new LinkedList<>()

        @Override
        void enableProgress(long totalSteps) {
            throw new UnsupportedOperationException()
        }

        @Override
        void updateProgress(long workedSteps) {
            throw new UnsupportedOperationException()
        }

        synchronized boolean isChanged() {
            return !queue.isEmpty()
        }

        @Override
        synchronized void print(Object o) {
            def outputChanged = queue.isEmpty()
            queue.add(o)
            if (outputChanged) {
                sendNotification(createNotification("outputChanged", "boolean", false, true))
            }
        }

        synchronized String consumeOutput() {
            if (!queue.isEmpty()) {
                def prints = queue.collect()
                queue.clear()
                sendNotification(createNotification("outputChanged", "boolean", true, false))
                return prints.join("")
            } else {
                return null
            }
        }

        @Override
        synchronized void println(Object o) {
            print("${o}\n")
        }
    }

    EvalOutput output

    private ExecutorService executor = Executors.newFixedThreadPool(1)

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
        if (future == null || !future.isDone()) throw new IllegalStateException("not yet finished")
        return future.get()
    }

    @Override
    void run() {
        output = new EvalOutput()
        sendNotification(createNotification("running", "boolean", false, true))
        future = glimpseShell.evaluate(script, output)
        executor.submit({
            while (!future.isDone()) {
                Thread.yield()
            }
            sendNotification(createNotification("running", "boolean", true, false))
            sendNotification(createNotification("finished", "boolean", false, true))
        } as Runnable)
    }

    Notification createNotification(String attributeName, attributeType, Object oldValue, Object newValue) {
        return new AttributeChangeNotification(this, sequence++, System.currentTimeMillis(), "${attributeName} changed", attributeName, attributeType, oldValue, newValue)
    }

    @Override
    boolean isRunning() {
        return future != null && !future.isDone()
    }

    @Override
    boolean isProgressEnabled() {
        return progressEnabled
    }

    @Override
    long getTotalSteps() {
        if (!progressEnabled) throw new IllegalStateException("progress is not enabled")
    }

    @Override
    long getWorkedSteps() {
        if (!progressEnabled) throw new IllegalStateException("progress is not enabled")
    }

    @Override
    boolean isOutputChanged() {
        return output != null && output.isChanged()
    }

    @Override
    String getOutputSinceLastChange() {
        if (output == null) throw new IllegalStateException("not yet running")
        return output.consumeOutput()
    }
}
