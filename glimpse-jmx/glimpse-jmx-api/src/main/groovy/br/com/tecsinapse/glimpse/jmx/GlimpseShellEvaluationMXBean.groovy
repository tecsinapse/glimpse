package br.com.tecsinapse.glimpse.jmx

interface GlimpseShellEvaluationMXBean {

    String getId()

    String getScript()

    boolean isFinished()

    String getResult()

}
