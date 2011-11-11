package br.com.tecsinapse.glimpse.server.sunhttp;

import br.com.tecsinapse.glimpse.server.BeginPoll;
import br.com.tecsinapse.glimpse.server.CancelPoll;
import br.com.tecsinapse.glimpse.server.ClosePoll;
import br.com.tecsinapse.glimpse.server.ServerPoll;
import br.com.tecsinapse.glimpse.server.StreamUpdatePoll;
import br.com.tecsinapse.glimpse.server.WorkedPoll;

public class PollWriter {

	public static String write(ServerPoll serverPoll) {
		if (serverPoll instanceof BeginPoll) {
			return "begin\n" + ((BeginPoll) serverPoll).getSteps();
		} else if (serverPoll instanceof CancelPoll) {
			return "cancel";
		} else if (serverPoll instanceof ClosePoll) {
			return "close";
		} else if (serverPoll instanceof WorkedPoll) {
			return "worked\n" + ((WorkedPoll) serverPoll).getWorkedSteps();
		} else if (serverPoll instanceof StreamUpdatePoll) {
			return "update\n" + ((StreamUpdatePoll) serverPoll).getUpdate();
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
}
