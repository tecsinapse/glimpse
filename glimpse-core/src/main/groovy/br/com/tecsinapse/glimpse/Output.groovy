package br.com.tecsinapse.glimpse

public interface Output {

    void enableProgress(long totalSteps)

    void updateProgress(long workedSteps)

    void print(Object o)

    void println(Object o)

}