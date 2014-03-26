package br.com.tecsinapse.glimpse.jmx

import com.sun.tools.attach.VirtualMachine
import com.sun.tools.attach.VirtualMachineDescriptor

import javax.management.JMX
import javax.management.ObjectName
import javax.management.remote.JMXConnectorFactory
import javax.management.remote.JMXServiceURL

class JmxGlimpseFactory {

    private static final String CONNECTOR_ADDRESS =
            "com.sun.management.jmxremote.localConnectorAddress";
    public static final String GLIMPSE_OBJECT_NAME = "br.com.tecsinapse.glimpse:type=Glimpse"

    static JmxGlimpse create(String serviceUrl) {
        def jmxServiceUrl = new JMXServiceURL(serviceUrl)
        def connector = JMXConnectorFactory.connect(jmxServiceUrl)
        def connection = connector.getMBeanServerConnection()
        def objectName = new ObjectName(GLIMPSE_OBJECT_NAME)
        def mxBean = JMX.newMBeanProxy(connection, objectName, GlimpseMXBean)
        return new JmxGlimpse(mxBean, new DefaultGlimpseShellMXBeanFinder(connection), new DefaultGlimpseShellEvaluationMXBeanFinder(connection))
    }

    static List<JmxGlimpseDescriptor> listGlimpses() {
        def vms = listVms()
        return vms.findAll {
            isGlimpseVm(it)
        }
    }

    private static List<JmxGlimpseDescriptor> listVms() {
        def vms = VirtualMachine.list()
        return vms.collect { VirtualMachineDescriptor vm ->
            new JmxGlimpseDescriptor() {

                @Override
                String getVmId() {
                    return vm.id()
                }

                @Override
                String getVmDisplayName() {
                    return vm.displayName()
                }

                @Override
                String getServiceUrl() {
                    return getServiceUrl(vm)
                }

                @Override
                String toString() {
                    return "${getVmId()} - ${getServiceUrl()}"
                }
            }
        }
    }

    private static String getServiceUrl(VirtualMachineDescriptor vmd) {
        VirtualMachine vm = VirtualMachine.attach(vmd.id())
        String serviceUrl = vm.getAgentProperties().getProperty(CONNECTOR_ADDRESS);

        if (serviceUrl == null) {
            vm.loadAgent(vm.getSystemProperties().getProperty("java.home") + File.separator + "lib" + File.separator + "management-agent.jar");

            serviceUrl = vm.getAgentProperties().getProperty(CONNECTOR_ADDRESS);
        }

        return serviceUrl
    }

    private static boolean isGlimpseVm(JmxGlimpseDescriptor vm) {
        def jmxServiceUrl = new JMXServiceURL(vm.getServiceUrl())
        def connector = JMXConnectorFactory.connect(jmxServiceUrl)
        def connection = connector.getMBeanServerConnection()
        def objectName = new ObjectName(GLIMPSE_OBJECT_NAME)
        try {
            connection.getObjectInstance(objectName)
            return true
        } catch (InstanceNotFoundException) {
            return false
        }
    }

}
