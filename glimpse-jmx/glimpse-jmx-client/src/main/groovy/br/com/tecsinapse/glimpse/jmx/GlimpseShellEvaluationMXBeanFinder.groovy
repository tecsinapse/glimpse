package br.com.tecsinapse.glimpse.jmx

import br.com.tecsinapse.glimpse.Output

interface GlimpseShellEvaluationMXBeanFinder {

    GlimpseShellEvaluationMXBean find(String shellId, String evalId, Output output)

}
