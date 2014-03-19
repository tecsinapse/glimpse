package br.com.tecsinapse.glimpse.jmx

public interface GlimpseShellMXBean {

    String getId()

    void setParameter(String param, String value)

    void evaluate(String script)

    boolean isEvaluating()

    boolean isProgressEnabled()

    int getTotalSteps()

    int getWorkedSteps()

    boolean isOutputChanged()

    boolean isFinished()

    String getOutputSinceLastChange()

}