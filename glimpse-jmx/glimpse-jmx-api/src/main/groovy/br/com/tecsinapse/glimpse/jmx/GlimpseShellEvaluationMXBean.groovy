package br.com.tecsinapse.glimpse.jmx

interface GlimpseShellEvaluationMXBean {

    String getId()

    String getScript()

    void run()

    boolean isRunning()

    boolean isProgressEnabled()

    long getTotalSteps()

    long getWorkedSteps()

    boolean isOutputChanged()

    String getOutputSinceLastChange()

    boolean isFinished()

    String getResult()

}
