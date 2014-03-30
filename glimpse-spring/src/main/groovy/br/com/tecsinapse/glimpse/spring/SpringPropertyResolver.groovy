package br.com.tecsinapse.glimpse.spring

import br.com.tecsinapse.glimpse.groovy.PropertyResolver
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

class SpringPropertyResolver implements PropertyResolver, ApplicationContextAware {

    private ApplicationContext applicationContext

    @Override
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext
    }

    @Override
    Object getProperty(String property) throws MissingPropertyException {
        return applicationContext.getBean(property)
    }


}
