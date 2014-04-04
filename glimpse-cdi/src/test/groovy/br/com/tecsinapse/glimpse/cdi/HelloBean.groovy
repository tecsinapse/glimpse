package br.com.tecsinapse.glimpse.cdi

import javax.inject.Named

@Named
class HelloBean {

    def hello() {
        return "hello"
    }

}
