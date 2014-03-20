package br.com.tecsinapse.glimpse.groovy

public interface PropertyResolver {

    Object getProperty(String name) throws MissingPropertyException

}