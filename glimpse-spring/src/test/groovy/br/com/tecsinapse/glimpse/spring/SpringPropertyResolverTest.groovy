package br.com.tecsinapse.glimpse.spring

import br.com.tecsinapse.glimpse.Output
import br.com.tecsinapse.glimpse.groovy.GroovyGlimpseShell
import org.springframework.context.support.ClassPathXmlApplicationContext
import spock.lang.Specification

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
        setup:
        def builder = new StringBuilder()
        def output = [

            print: { o ->
                builder.append(o)
            },

            println: { o ->
                builder.append(o)
                builder.append("\n")
            }

        ] as Output

        when:
        def result = shell.evaluate("noSuchBean.hello", output).get()

        then:
        builder.toString().contains("NoSuchBeanDefinitionException")
        result == null
    }

}
