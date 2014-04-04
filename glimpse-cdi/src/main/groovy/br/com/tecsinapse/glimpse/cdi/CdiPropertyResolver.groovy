package br.com.tecsinapse.glimpse.cdi

import br.com.tecsinapse.glimpse.groovy.PropertyResolver
import org.apache.deltaspike.core.api.provider.BeanProvider

class CdiPropertyResolver implements PropertyResolver {

    Object getProperty(String name) {
        return BeanProvider.getContextualReference(name)
    }

}
