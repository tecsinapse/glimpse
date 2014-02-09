package br.com.tecsinapse.glimpse.server.jmx;

import br.com.tecsinapse.glimpse.protocol.jmx.ServerMXBean;
import br.com.tecsinapse.glimpse.server.Server;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class ServerMXBeanImpl implements ServerMXBean {
	private final Server server;

	public ServerMXBeanImpl(Server server) {
		this.server = server;
	}

	@Override
	public String start(String script, String[][] params) {
		List<String[]> paramsList = Lists.newArrayList(params);
		Map<String, String> paramsMap = Maps.transformValues(Maps.uniqueIndex(paramsList, new Function<String[], String>() {
			@Override
			public String apply(String[] o) {
				return o[0];
			}
		}), new Function<String[], String>() {
			@Override
			public String apply(String[] strings) {
				return strings[1];
			}
		});
		return server.start(script, paramsMap);
	}
}
