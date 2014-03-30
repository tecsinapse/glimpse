package br.com.tecsinapse.glimpse.jmx

import br.com.tecsinapse.glimpse.GlimpseShell

import javax.management.ObjectName
import java.lang.management.ManagementFactory

class GlimpseShellMXBeanImpl implements GlimpseShellMXBean {

    private String id

    private GlimpseShell shell

    private List<String> evalIds = []

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
    String evaluate(String script) {
        String evalId = UUID.randomUUID().toString()
        def evaluation = new GlimpseShellEvaluationMXBeanImpl(evalId, script, shell)
        def objectName = new ObjectName("br.com.tecsinapse.glimpse:type=Evaluation,id=${evalId},shellId=${id}")
        ManagementFactory.getPlatformMBeanServer().registerMBean(evaluation, objectName)
        evalIds << evalId
        return evalId
    }

    @Override
    void destroyEvaluation(String evalId) {
        def objectName = new ObjectName("br.com.tecsinapse.glimpse:type=Evaluation,id=${evalId},shellId=${id}")
        ManagementFactory.getPlatformMBeanServer().unregisterMBean(objectName)
        evalIds.remove(evalId)
    }

    void destroyAllEvaluations() {
        evalIds.clone().each {
            destroyEvaluation(it)
        }
    }
}
