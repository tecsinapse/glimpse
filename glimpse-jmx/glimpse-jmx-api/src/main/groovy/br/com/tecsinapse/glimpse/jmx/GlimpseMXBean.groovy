package br.com.tecsinapse.glimpse.jmx

public interface GlimpseMXBean {

    String createShell()

    void setParameter(String shellId, String param, String value)

    void destroyShell(String shellId)

}