package br.com.tecsinapse.glimpse.spring

import br.com.tecsinapse.glimpse.groovy.GroovyGlimpseShell
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.context.support.ClassPathXmlApplicationContext
import spock.lang.Specification

import java.util.concurrent.ExecutionException

class SpringPropertyResolverTest extends Specification {

    def propertyResolver = new SpringPropertyResolver()
    def shell = new GroovyGlimpseShell(propertyResolver, null)

    def setup() {
        def applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml")
        propertyResolver.setApplicationContext(applicationContext)
    }

    def "existent bean"() {
        expect:
        shell.evaluate("testBean.hello", null).get() == "hello"
    }

    def "missing bean"() {
        when:
        shell.evaluate("noSuchBean.hello", null).get()

        then:
        ExecutionException e = thrown()
        e.cause instanceof NoSuchBeanDefinitionException
    }

}
