package br.com.tecsinapse.glimpse.cli;

public class DumbOutput implements Output {

	private StringBuilder output = new StringBuilder();

	@Override
	public void println(String s) {
		output.append(s);
		output.append("\n");
	}

	public String getOutput() {
		return output.toString();
	}

}
