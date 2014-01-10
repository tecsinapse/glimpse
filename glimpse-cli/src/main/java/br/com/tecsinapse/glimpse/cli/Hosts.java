package br.com.tecsinapse.glimpse.cli;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.removeIf;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Iterables.tryFind;

@XmlRootElement(name = "hosts")
public class Hosts {

	@XmlAnyElement(lax=true)
	private List<HostSpec> hosts = new ArrayList<HostSpec>();

	private transient FileSystem fileSystem;

	public static Hosts parse(FileSystem fileSystem) {
		try {
			JAXBContext context = getJaxbContext();
			Hosts hosts = (Hosts) context.createUnmarshaller().unmarshal(
					new ByteArrayInputStream(fileSystem.readHostsFile().getBytes()));
			hosts.fileSystem = fileSystem;
			return hosts;
		} catch (JAXBException e) {
			throw new IllegalStateException(e);
		}
	}

	private static JAXBContext getJaxbContext() throws JAXBException {
		return JAXBContext.newInstance(Hosts.class, HostSpec.class);
	}

	public void write() {
		try {
			JAXBContext context = getJaxbContext();
			StringWriter writer = new StringWriter();
			context.createMarshaller().marshal(this, writer);
			fileSystem.writeHostsFile(writer.toString());
		} catch (JAXBException e) {
			throw new IllegalStateException(e);
		}

	}

	public Host getByName(final String hostName) {
		Optional<HostSpec> optionalSpec = tryFind(hosts, new Predicate<HostSpec>() {
			@Override
			public boolean apply(HostSpec hostSpec) {
				return hostSpec.getName().equals(hostName);
			}
		});
		if (!optionalSpec.isPresent()) return null;
		return new DefaultHost(optionalSpec.get(), fileSystem);
	}


	public Host getDefaultHost() {
		Optional<HostSpec> optionalSpec = tryFind(hosts, new Predicate<HostSpec>() {
			@Override
			public boolean apply(HostSpec hostSpec) {
				return hostSpec.isDefaultHost();
			}
		});
		if (!optionalSpec.isPresent()) return null;
		return new DefaultHost(optionalSpec.get(), fileSystem);
	}

	public List<HostSpec> getHostSpecs() {
		return hosts;
	}

	public void addHost(final HostSpec hostSpec) {
		if (tryFind(hosts, new Predicate<HostSpec>() {
			@Override
			public boolean apply(HostSpec h) {
				return h.getName().equals(hostSpec.getName());
			}
		}).isPresent()) throw new IllegalArgumentException(String.format("Host '%s' already exists", hostSpec.getName()));
		if (hostSpec.isDefaultHost()) {
			hosts = Lists.newArrayList(transform(hosts, new Function<HostSpec, HostSpec>() {
				@Override
				public HostSpec apply(HostSpec hostSpec) {
					if (!hostSpec.isDefaultHost()) return hostSpec;
					return hostSpec.asNonDefault();
				}
			}));
		}
		hosts.add(hostSpec);
		write();
	}

	public void deleteHost(final String hostName) {
		if (!removeIf(hosts, new Predicate<HostSpec>() {
			@Override
			public boolean apply(HostSpec hostSpec) {
				return hostSpec.getName().equals(hostName);
			}
		})) throw new IllegalArgumentException(String.format("No such host '%s'", hostName));
		write();
	}

	public void setDefaultHost(final String hostName) {
		if (!tryFind(hosts, new Predicate<HostSpec>() {
			@Override
			public boolean apply(HostSpec hostSpec) {
				return hostSpec.getName().equals(hostName);
			}
		}).isPresent()) throw new IllegalArgumentException(String.format("No such host: '%s'", hostName));
		hosts = Lists.newArrayList(transform(hosts, new Function<HostSpec, HostSpec>() {
			@Nullable
			@Override
			public HostSpec apply(HostSpec hostSpec) {
				return hostSpec.getName().equals(hostName) ? hostSpec.asDefault() : hostSpec.asNonDefault();
			}
		}));
		write();
	}
}
