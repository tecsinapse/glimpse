package br.com.tecsinapse.glimpse.groovy

import br.com.tecsinapse.glimpse.GlimpseShell
import br.com.tecsinapse.glimpse.GlimpseShellFactory

class GroovyGlimpseShellFactory implements GlimpseShellFactory {

    PropertyResolver propertyResolver

    GroovyGlimpseShellFactory(PropertyResolver propertyResolver) {
        this.propertyResolver = propertyResolver
    }

    @Override
    GlimpseShell create() {
        return new GroovyGlimpseShell(propertyResolver)
    }
}
