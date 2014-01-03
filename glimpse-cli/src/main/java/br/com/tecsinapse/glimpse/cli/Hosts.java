package br.com.tecsinapse.glimpse.cli;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import javax.annotation.Nullable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "hosts")
public class Hosts {

	@XmlAnyElement(lax=true)
	private List<DefaultHost> hosts = new ArrayList<DefaultHost>();

	public Map<String, Host> getHostsByName() {
		return Maps.<String, Host>newHashMap(Maps.uniqueIndex(hosts, new Function<DefaultHost, String>() {
			@Nullable
			@Override
			public String apply(@Nullable DefaultHost host) {
				return host.getName();
			}
		}));
	}

	public Host getDefaultHost() {
		return Iterables.find(hosts, new Predicate<DefaultHost>() {
			@Override
			public boolean apply(@Nullable DefaultHost host) {
				return host.isDefaultHost();
			}
		}, null);
	}

	public static Hosts parse(String xml) {
		try {
			JAXBContext context = JAXBContext.newInstance(Hosts.class, DefaultHost.class);
			return (Hosts) context.createUnmarshaller().unmarshal(
					new ByteArrayInputStream(xml.getBytes()));
		} catch (JAXBException e) {
			throw new IllegalStateException(e);
		}
	}

}