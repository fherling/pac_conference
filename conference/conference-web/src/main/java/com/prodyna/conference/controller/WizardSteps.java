package com.prodyna.conference.controller;

public enum WizardSteps {

	ALL_CONFERENCES(0), NEW_CONFERENCE(1), SHOW_CONFERENCE(2), EDIT_CONFERENCE(
			3),

	ALL_ROOMS(20), SHOW_ROOM(21), EDIT_ROOM(22), NEW_ROOM(23),

	ALL_SPEAKERS(30), SHOW_SPEAKER(31), EDIT_SPEAKER(32), NEW_SPEAKER(33),

	ALL_TALKS(40), SHOW_TALK(41), EDIT_TALK(42), ALL_TALKS_FOR_CONFERENCE(43), NEW_TALK(44)

	;

	private int step;

	private WizardSteps(int pStep) {
		this.step = pStep;
	}

	public int getStep() {
		return step;
	}

}
