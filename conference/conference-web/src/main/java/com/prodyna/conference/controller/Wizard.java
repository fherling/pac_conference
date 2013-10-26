package com.prodyna.conference.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.Talk;

@Named
@SessionScoped
public class Wizard {

	@Inject
	private Logger log;
	
	private WizardSteps step = WizardSteps.ALL_CONFERENCES;
	
	private Conference conference;
	
	private Talk talk;
	
	private Speaker speaker;
	
	private Room room;

	private List<Room> rooms;
	private List<Talk> talks;
	private List<Conference> conferences;
	private List<Speaker> speakers;
	
	@PostConstruct
    public void init() {
		log.info("Init Wizard Bean");
	}
	
	
	public void newConference(){
		log.info("newConference");
		conference = new Conference();
		step = WizardSteps.SHOW_CONFERENCE;
	}
	
	public void editConference(){
		log.info("editConference");
		step = WizardSteps.EDIT_CONFERENCE;
	}




	public WizardSteps getStep() {
		return step;
	}




	public void setStep(WizardSteps step) {
		this.step = step;
	}
}
