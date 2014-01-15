package br.com.tecsinapse.glimpse.cli;

public class ProgressController {

	private static final String[] PROGRESS_ICONS = new String[] {"|", "/", "-", "\\"};
	private static final int TOTAL_NUMBER_OF_BARS = 50;
	private int lastProgressIcon = 0;

	private boolean progressBarEnabled;
	private double percentWorked;

	public synchronized String getText() {
		if (progressBarEnabled) {
			return getProgressBarText();
		} else {
			return getProgressText();
		}
	}

	private String getProgressText() {
		if (lastProgressIcon == PROGRESS_ICONS.length) {
			lastProgressIcon = 0;
		}
		return PROGRESS_ICONS[lastProgressIcon++];
	}

	private String getProgressBarText() {
		double ratio = percentWorked;
		int numberOfBars = (int) (ratio * TOTAL_NUMBER_OF_BARS);
		int numberOfSpaces = TOTAL_NUMBER_OF_BARS - numberOfBars;
		StringBuilder result = new StringBuilder();
		result.append(getProgressText());
		result.append(" [");
		for (int i = 0; i < numberOfBars; i++) result.append("=");
		for (int i = 0; i < numberOfSpaces; i++) result.append(" ");
		result.append("] ");
		result.append((long) (ratio * 100));
		result.append("%");
		return result.toString();
	}

	public synchronized void enableProgressBar() {
		this.progressBarEnabled = true;
	}

	public synchronized void updateProgressBar(float percentWorked) {
		this.percentWorked = percentWorked;
	}
}
