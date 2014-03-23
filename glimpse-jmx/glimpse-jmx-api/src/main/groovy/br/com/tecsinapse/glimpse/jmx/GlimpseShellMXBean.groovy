package br.com.tecsinapse.glimpse.jmx

public interface GlimpseShellMXBean {

    String getId()

    void setParameter(String param, String value)

    String evaluate(String script)

    void destroyEvaluation(String id)

}