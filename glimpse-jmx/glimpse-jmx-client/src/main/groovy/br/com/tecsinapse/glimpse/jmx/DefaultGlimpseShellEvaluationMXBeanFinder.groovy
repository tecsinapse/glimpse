package br.com.tecsinapse.glimpse.jmx

import br.com.tecsinapse.glimpse.Output

import javax.management.*

class DefaultGlimpseShellEvaluationMXBeanFinder implements GlimpseShellEvaluationMXBeanFinder {

    private MBeanServerConnection mBeanServerConnection

    DefaultGlimpseShellEvaluationMXBeanFinder(MBeanServerConnection mBeanServerConnection) {
        this.mBeanServerConnection = mBeanServerConnection
    }

    @Override
    GlimpseShellEvaluationMXBean find(String shellId, String evalId, Output output) {
        def objectName = new ObjectName("br.com.tecsinapse.glimpse:type=Evaluation,id=${evalId},shellId=${shellId}")
        def mxBean = JMX.newMBeanProxy(mBeanServerConnection, objectName, GlimpseShellEvaluationMXBean)
        mBeanServerConnection.addNotificationListener(objectName, { Notification n, hb ->

            def an = (AttributeChangeNotification) n
            if (an.attributeName == 'outputChanged') {
                def o = mxBean.getOutputSinceLastChange()
                output.print(o)
            }

        } as NotificationListener, null, null)
        return mxBean
    }
}
