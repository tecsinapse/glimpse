package br.com.tecsinapse.glimpse.protocol;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "param")
public class ParamValue {

	@XmlElement
	private String name;

	@XmlElement
	private String value;

	@SuppressWarnings("UnusedDeclaration")
	ParamValue() {
	}

	public ParamValue(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	@SuppressWarnings("UnusedDeclaration")
	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ParamValue that = (ParamValue) o;

		return name.equals(that.name) && value.equals(that.value);
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + value.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "ParamValue{" +
				"name='" + name + '\'' +
				", value='" + value + '\'' +
				'}';
	}
}
