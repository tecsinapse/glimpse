package br.com.tecsinapse.glimpse.client.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import br.com.tecsinapse.glimpse.client.BeginPoll;
import br.com.tecsinapse.glimpse.client.CancelPoll;
import br.com.tecsinapse.glimpse.client.ClientPoll;
import br.com.tecsinapse.glimpse.client.ClosePoll;
import br.com.tecsinapse.glimpse.client.StreamUpdatePoll;
import br.com.tecsinapse.glimpse.client.WorkedPoll;

public class ClientPollConverter {

	public static List<ClientPoll> convert(String body) {
		List<ClientPoll> result = new LinkedList<ClientPoll>();
		StringReader reader = new StringReader(body);
		BufferedReader br = new BufferedReader(reader);
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				String command = line;
				if ("cancel".equals(command)) {
					result.add(new CancelPoll());
				} else if ("close".equals(command)) {
					result.add(new ClosePoll());
				} else if ("begin".equals(command)) {
					String steps = br.readLine();
					result.add(new BeginPoll(Integer.parseInt(steps)));
				} else if ("worked".equals(command)) {
					String steps = br.readLine();
					result.add(new WorkedPoll(Integer.parseInt(steps)));
				} else if ("update".equals(command)) {
					String update = br.readLine();
					result.add(new StreamUpdatePoll(update));
				}
			}
		} catch (NumberFormatException e) {
			throw new IllegalStateException(e);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return result;
	}
}
