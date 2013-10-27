package com.prodyna.conference.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;

import com.prodyna.conference.business.service.BusinessService;
import com.prodyna.conference.service.AssignService;
import com.prodyna.conference.service.ConferenceService;
import com.prodyna.conference.service.RoomService;
import com.prodyna.conference.service.SpeakerService;
import com.prodyna.conference.service.TalkService;
import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.Talk;

@ManagedBean
@SessionScoped
public class Wizard extends AbstractViewController implements Serializable {

	private static final long serialVersionUID = -7871091181591236974L;

	private WizardSteps step = WizardSteps.ALL_CONFERENCES;

	@Inject
	private ValueContainer valueContainer = new ValueContainer();

	@Inject
	private BusinessService businessService;

	@Inject
	private AssignService assignService;

	@Inject
	private ConferenceService conferenceService;

	@Inject
	private RoomService roomService;

	@Inject
	private SpeakerService speakerService;
	@Inject
	private TalkService talkService;

	public void create(ActionEvent event) {

		log.info("create new entry");
		switch (getStep()) {
		case ALL_CONFERENCES:
		case SHOW_CONFERENCE:
		case EDIT_CONFERENCE:
			valueContainer.newConference();
			step = WizardSteps.NEW_CONFERENCE;
			break;

		case ALL_ROOMS:
		case SHOW_ROOM:
		case EDIT_ROOM:
			valueContainer.newRoom();
			step = WizardSteps.NEW_ROOM;
			break;

		case ALL_SPEAKERS:
		case SHOW_SPEAKER:
		case EDIT_SPEAKER:
			valueContainer.newSpeaker();
			step = WizardSteps.NEW_SPEAKER;
			break;
		case NEW_TALK:
		case SHOW_TALK:
		case EDIT_TALK:
			valueContainer.newTalk();
			step = WizardSteps.NEW_TALK;
			break;

		default:
			break;
		}

	}

	public void createTalk(ActionEvent event) {
		valueContainer.newTalk();
		step = WizardSteps.NEW_TALK;
		List<Room> rooms = roomService.loadRooms();
		List<Speaker> speakers = speakerService.loadSpeakers();
		
		valueContainer.getSpeakers().clear();
		valueContainer.getSpeakers().addAll(speakers);
		valueContainer.getRooms().clear();
		valueContainer.getRooms().addAll(rooms);
	}

	public void editTalk(ActionEvent event) {
		log.info("editTalk");
		if (isAdmin()) {
			step = WizardSteps.EDIT_TALK;
		} else {
			step = WizardSteps.SHOW_TALK;
		}
		valueContainer.editTalk();

		Talk talk = valueContainer.getTalk();

		if (talk.getId() != null) {
			Room room = talkService.loadRoomFor(valueContainer.getTalk());
			List<Speaker> talkSpaker = talkService
					.loadSpeakersFor(valueContainer.getTalk());
			
			valueContainer.setRoom(room);
			valueContainer.getSpeakersSelected().clear();
			valueContainer.getSpeakersSelected().addAll(talkSpaker);
		}

		List<Room> rooms = roomService.loadRooms();
		List<Speaker> speakers = speakerService.loadSpeakers();
		
		valueContainer.getSpeakers().clear();
		valueContainer.getSpeakers().addAll(speakers);
		valueContainer.getRooms().clear();
		valueContainer.getRooms().addAll(rooms);
		
	}

	public void edit(ActionEvent event) {
		log.info("edit");

		switch (step) {
		case ALL_CONFERENCES:
		case SHOW_CONFERENCE:
			if (isAdmin()) {
				step = WizardSteps.EDIT_CONFERENCE;
			} else {
				step = WizardSteps.SHOW_CONFERENCE;
			}

			valueContainer.editConference();
			List<Talk> talks = conferenceService.loadTalksFor(valueContainer
					.getConference());
			valueContainer.loadTalks(talks);

			break;
		case ALL_SPEAKERS:
		case SHOW_SPEAKER:
			if (isAdmin()) {
				step = WizardSteps.EDIT_SPEAKER;
			} else {
				step = WizardSteps.SHOW_SPEAKER;
			}
			valueContainer.editSpeaker();
			break;
		case ALL_ROOMS:
		case SHOW_ROOM:
			if (isAdmin()) {
				step = WizardSteps.EDIT_ROOM;
			} else {
				step = WizardSteps.SHOW_ROOM;
			}
			valueContainer.editRoom();
			break;

		default:
			break;
		}

	}

	public void showConference(ActionEvent event) {
		log.info("showConference");
		step = WizardSteps.SHOW_CONFERENCE;
	}

	public void showAllConferences(ActionEvent event) {
		step = WizardSteps.ALL_CONFERENCES;

		List<Conference> result = conferenceService.loadConferences();

		valueContainer.loadConferences(result);

	}

	public void delete(ActionEvent event) {
		try {

			if (!isDeleteAllowed()) {
				return;
			}
			switch (getStep()) {
			case ALL_CONFERENCES:
				valueContainer.editConference();
				conferenceService.delete(valueContainer.getConference());
				valueContainer.deleteConference();
				step = WizardSteps.ALL_CONFERENCES;
				break;
			case EDIT_CONFERENCE:
				valueContainer.editConference();
				conferenceService.delete(valueContainer.getConference());
				valueContainer.deleteConference();
				step = WizardSteps.ALL_CONFERENCES;
				break;
			case ALL_ROOMS:
				valueContainer.editRoom();
				roomService.delete(valueContainer.getRoom());
				valueContainer.deleteRoom();
				break;
			case EDIT_ROOM:
				valueContainer.editRoom();
				roomService.delete(valueContainer.getRoom());
				valueContainer.deleteRoom();
				if (valueContainer.getConference() != null) {
					step = WizardSteps.SHOW_CONFERENCE;
				} else {
					step = WizardSteps.ALL_SPEAKERS;
				}
				break;
			case ALL_SPEAKERS:
				valueContainer.editSpeaker();
				speakerService.delete(valueContainer.getSpeaker());
				valueContainer.deleteSpeaker();
				break;
			case EDIT_SPEAKER:
				valueContainer.editSpeaker();
				speakerService.delete(valueContainer.getSpeaker());
				valueContainer.deleteSpeaker();
				if (valueContainer.getConference() != null) {
					step = WizardSteps.SHOW_CONFERENCE;
				} else {
					step = WizardSteps.ALL_SPEAKERS;
				}
				break;
			default:
				log.info("WRONG STEP FOR CALLING SAVE");
				String errorMessage = "Delete is not allowed";
				FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						errorMessage, "Could not save data");
				facesContext.addMessage(null, m);
				break;

			}

		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Could not save data");
			facesContext.addMessage(null, m);
		}

	}

	public void deleteTalk(ActionEvent event) {
		valueContainer.editTalk();
		assignService.unassign(valueContainer.getConference(),
				valueContainer.getTalk());
		talkService.delete(valueContainer.getTalk());
		valueContainer.deleteTalk();
		if (getStep() == WizardSteps.SHOW_TALK
				|| getStep() == WizardSteps.EDIT_TALK) {
			step = WizardSteps.SHOW_CONFERENCE;
		}
	}

	private boolean isDeleteAllowed() {
		if (!isAdmin()) {
			log.info("NOT IN ADMINMODUS");
			String errorMessage = getMessageText("DELETE_NOT_POSSIBLE");
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, errorMessage);
			facesContext.addMessage(null, m);
			return false;
		}
		return true;
	}

	public void showAllTalksForConference(ActionEvent event) {
		step = WizardSteps.ALL_TALKS_FOR_CONFERENCE;
	}

	public void showAllTalks(ActionEvent event) {
		step = WizardSteps.ALL_TALKS;
	}

	public String getHeader() {
		return getStep().name();
	}

	public WizardSteps getStep() {
		return step;
	}

	public void setStep(WizardSteps step) {
		this.step = step;
	}

	public Date getDate() {
		return new Date();
	}

	public ValueContainer getValueContainer() {
		return valueContainer;
	}

	public void save(ActionEvent event) {

		try {
			Conference conference = null;
			Room room = null;
			Speaker speaker = null;
			Talk talk = null;
			switch (getStep()) {
			case EDIT_CONFERENCE:
				conference = conferenceService.save(valueContainer
						.getConference());
				valueContainer.setConference(conference);
				break;
			case NEW_CONFERENCE:
				conference = conferenceService.save(valueContainer
						.getConference());
				valueContainer.setConference(conference);
				break;
			case EDIT_ROOM:
				room = roomService.save(valueContainer.getRoom());
				valueContainer.setRoom(room);
				break;
			case NEW_ROOM:
				room = roomService.save(valueContainer.getRoom());
				valueContainer.setRoom(room);
				break;
			case EDIT_SPEAKER:
				speaker = speakerService.save(valueContainer.getSpeaker());
				valueContainer.setSpeaker(speaker);
				break;
			case NEW_SPEAKER:
				speaker = speakerService.save(valueContainer.getSpeaker());
				valueContainer.setSpeaker(speaker);
				break;
			case NEW_TALK:
				talk = businessService.saveAndAssignTalk(
						valueContainer.getTalk(),
						valueContainer.getConference(),
						valueContainer.getSpeakersSelected(), 
						valueContainer.getRoom());
				valueContainer.setTalk(talk);
				valueContainer.getTalks().add(talk);
				break;
			case EDIT_TALK:
				talk = talkService.save(valueContainer.getTalk());
				valueContainer.setTalk(talk);
				break;
			default:
				break;
			}
			String errorMessage = getMessageText("data_saved");
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO,
					errorMessage, errorMessage);
			facesContext.addMessage(null, m);

		} catch (Exception e) {
			String summary = getMessageText("data_not_saved");
			String detail = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					summary, detail);
			facesContext.addMessage(null, m);
		}
	}

	public boolean isSaveRendered() {

		if (!isAdmin()) {
			return false;
		}

		switch (getStep()) {
		case EDIT_CONFERENCE:
		case NEW_CONFERENCE:
		case EDIT_SPEAKER:
		case NEW_SPEAKER:
		case EDIT_ROOM:
		case NEW_ROOM:
		case NEW_TALK:
		case EDIT_TALK:
			return true;

		default:
			return false;

		}

	}

	public boolean isDeleteRendered() {
		if (!isAdmin()) {
			return false;
		}

		if (getStep() == WizardSteps.EDIT_CONFERENCE
				// || getStep() == WizardSteps.EDIT_TALK
				// || getStep() == WizardSteps.EDIT_SPEAKER
				// || getStep() == WizardSteps.EDIT_ROOM
				|| getStep() == WizardSteps.ALL_ROOMS
				|| getStep() == WizardSteps.ALL_SPEAKERS
				|| getStep() == WizardSteps.ALL_CONFERENCES) {
			return true;
		}
		return false;
	}

	public boolean isNewRendered() {
		return true;
	}

	public boolean isEditRendered() {

		if (!isAdmin()) {
			return false;
		}

		if (getStep() == WizardSteps.SHOW_CONFERENCE
				|| getStep() == WizardSteps.SHOW_TALK
				|| getStep() == WizardSteps.SHOW_SPEAKER
				|| getStep() == WizardSteps.SHOW_ROOM) {
			return true;
		}

		return false;
	}

	public boolean isAdmin() {
		return true;

	}

	public String displayAllConferences() {
		step = WizardSteps.ALL_CONFERENCES;
		List<Conference> result = conferenceService.loadConferences();
		valueContainer.loadConferences(result);
		valueContainer.getTalks().clear();
		valueContainer.setConference(null);
		return "conference-wizard";
	}

	public String displayAllSpeakers() {
		step = WizardSteps.ALL_SPEAKERS;
		List<Speaker> result = speakerService.loadSpeakers();

		valueContainer.loadSpeakers(result);
		return "conference-wizard";
	}

	public String displayAllRooms() {
		step = WizardSteps.ALL_ROOMS;
		List<Room> result = roomService.loadRooms();

		valueContainer.loadRooms(result);
		return "conference-wizard";
	}

	public String goback() {
		switch (step) {
		case ALL_CONFERENCES:
		case ALL_ROOMS:
		case ALL_SPEAKERS:
			return "index";
		case EDIT_CONFERENCE:
		case SHOW_CONFERENCE:
		case NEW_CONFERENCE:
			return displayAllConferences();
		case EDIT_ROOM:
		case SHOW_ROOM:
		case NEW_ROOM:
			return displayAllRooms();
		case EDIT_SPEAKER:
		case SHOW_SPEAKER:
		case NEW_SPEAKER:
			return displayAllSpeakers();
		case EDIT_TALK:
		case NEW_TALK:
		case SHOW_TALK:
			step = WizardSteps.SHOW_CONFERENCE;
			return null;
		default:
			return "index";
		}
	}

	public String getMessageText(String key) {
		ResourceBundle text = ResourceBundle.getBundle(
				"com.prodyna.conference.messages", facesContext.getViewRoot()
						.getLocale());
		String value = text.getString(key);

		return value;
	}

	
	public ListDataModel<Talk> getTalksForRoom(){
		List<Talk> talks = talkService.loadTalksForRoom(valueContainer.getRoom());
		return new ListDataModel<Talk>(talks);
	}
	
	public ListDataModel<Talk> getTalksForSpeaker(){
		List<Talk> talks = talkService.loadTalksForSpeaker(valueContainer.getSpeaker());
		return new ListDataModel<Talk>(talks);
	}
}
