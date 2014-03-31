package br.com.tecsinapse.glimpse.groovy

public interface MethodResolver {

    def methodMissing(String name, args)

}