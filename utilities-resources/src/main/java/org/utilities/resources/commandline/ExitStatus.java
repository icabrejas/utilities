package org.utilities.resources.commandline;

public enum ExitStatus {

	OK(0), ERROR(1);

	private int value;

	private ExitStatus(int value) {
		this.value = value;
	}

	public void exit() {
		System.exit(value);
	}

}
