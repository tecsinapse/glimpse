package br.com.tecsinapse.glimpse.client.jmx;

import br.com.tecsinapse.glimpse.client.Connector;
import br.com.tecsinapse.glimpse.client.ConnectorException;
import br.com.tecsinapse.glimpse.protocol.PollResultItem;
import br.com.tecsinapse.glimpse.protocol.jmx.ServerMXBean;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JmxConnector implements Connector {

	private static final String CONNECTOR_ADDRESS =
			"com.sun.management.jmxremote.localConnectorAddress";
	private final ServerMXBean serverMXBean;
	private final MBeanServerConnection connection;

	public JmxConnector(String id) throws IOException, AttachNotSupportedException, MalformedObjectNameException, AgentLoadException, AgentInitializationException {
		VirtualMachine vm = VirtualMachine.attach(id);

		String serviceUrl = vm.getAgentProperties().getProperty(CONNECTOR_ADDRESS);

		if (serviceUrl == null) {
			vm.loadAgent(vm.getSystemProperties().getProperty("java.home") + File.separator + "lib" + File.separator + "management-agent.jar");

			serviceUrl = vm.getAgentProperties().getProperty(CONNECTOR_ADDRESS);
		}

		JMXServiceURL jmxServiceURL = new JMXServiceURL(serviceUrl);

		JMXConnector connector = JMXConnectorFactory.connect(jmxServiceURL);

		connection = connector.getMBeanServerConnection();

		ObjectName objectName = new ObjectName("br.com.tecsinapse.glimpse:type=Server");

		serverMXBean = JMX.newMBeanProxy(connection, objectName, ServerMXBean.class);
	}

	@Override
	public String start(String script, Map<String, String> params) throws ConnectorException {
		List<String[]> paramsList = Lists.newArrayList(Iterables.transform(params.entrySet(), new Function<Map.Entry<String, String>, String[]>() {
			@Override
			public String[] apply(Map.Entry<String, String> entry) {
				return new String[] {entry.getKey(), entry.getValue()};
			}
		}));
		return serverMXBean.start(script, paramsList.toArray(new String[][] {}));
	}

	@Override
	public boolean isOpen(String id) throws ConnectorException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<PollResultItem> poll(String id) throws ConnectorException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void cancel(String id) throws ConnectorException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String createRepl() throws ConnectorException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String eval(String replId, String script) throws ConnectorException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void closeRepl(String replId) throws ConnectorException {
		throw new UnsupportedOperationException();
	}
}
