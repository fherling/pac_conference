/**
 * 
 */
package com.prodyna.conference.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import com.prodyna.conference.converter.RoomConverter;
import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.Talk;

/**
 * @author fherling
 * 
 */
@Named
public class ValueContainer {

	private Conference conference;

	private Talk talk;

	private Speaker speaker;

	private Room room;

	private List<Room> rooms = new ArrayList<Room>();
	private List<Talk> talks = new ArrayList<Talk>();
	private List<Conference> conferences = new ArrayList<Conference>();
	private List<Speaker> speakers = new ArrayList<Speaker>();

	private List<Speaker> speakersSelected = new ArrayList<Speaker>();

	private ListDataModel<Conference> conferenceDataModel;
	private ListDataModel<Talk> talkDataModel;
	private ListDataModel<Room> roomDataModel;
	private ListDataModel<Speaker> speakerDataModel;

	public ValueContainer() {
		setSpeakerDataModel(new ListDataModel<Speaker>(speakers));
		setTalkDataModel(new ListDataModel<Talk>(talks));
		setRoomDataModel(new ListDataModel<Room>(rooms));
		setConferenceDataModel(new ListDataModel<Conference>(conferences));
	}

	public void newRoom() {
		room = new Room();
	}

	public void newSpeaker() {
		speaker = new Speaker();
	}

	public void newTalk() {
		talk = new Talk();
		speaker = null;
		room = null;
	}

	public void newConference() {
		conference = new Conference();
		talk = null;
		speaker = null;
		room = null;
	}

	public Conference getConference() {
		return conference;
	}

	public void setConference(Conference conference) {
		this.conference = conference;
	}

	public Talk getTalk() {
		return talk;
	}

	public void setTalk(Talk talk) {
		this.talk = talk;
	}

	public Speaker getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public List<Talk> getTalks() {
		return talks;
	}

	public void setTalks(List<Talk> talks) {
		this.talks = talks;
	}

	public List<Conference> getConferences() {
		return conferences;
	}

	public void setConferences(List<Conference> conferences) {
		this.conferences = conferences;
	}

	public List<Speaker> getSpeakers() {
		return speakers;
	}

	public void setSpeakers(List<Speaker> speakers) {
		this.speakers = speakers;
	}

	public ListDataModel<Conference> getConferenceDataModel() {
		return conferenceDataModel;
	}

	public void setConferenceDataModel(
			ListDataModel<Conference> conferenceDataModel) {
		this.conferenceDataModel = conferenceDataModel;
	}

	public void editConference() {
		if (null == conference) {
			conference = getConferenceDataModel().getRowData();
		}
		talk = null;
		speaker = null;
		room = null;
		speakersSelected.clear();
		talks.clear();
		rooms.clear();
	}

	public void editSpeaker() {

		speaker = getSpeakerDataModel().getRowData();
	}

	public void editRoom() {
		room = getRoomDataModel().getRowData();
	}

	public void editTalk() {
		talk = getTalkDataModel().getRowData();
	}

	public void deleteConference() {

		if (null == conference) {

			conference = getConferenceDataModel().getRowData();
		}

		getConferences().remove(conference);

		conference = new Conference();
		speakersSelected.clear();
		room = null;
		talk = null;
		speaker = null;
		talks.clear();

	}

	public void loadConferences(List<Conference> result) {

		conferences.clear();
		conferences.addAll(result);

	}

	public void loadTalks(List<Talk> result) {

		talks.clear();
		talks.addAll(result);

	}

	public void loadRooms(List<Room> result) {

		rooms.clear();
		rooms.addAll(result);

	}

	public void loadSpeakers(List<Speaker> result) {

		speakers.clear();
		speakers.addAll(result);

	}

	public ListDataModel<Talk> getTalkDataModel() {
		return talkDataModel;
	}

	public void setTalkDataModel(ListDataModel<Talk> talkDataModel) {
		this.talkDataModel = talkDataModel;
	}

	public ListDataModel<Room> getRoomDataModel() {
		return roomDataModel;
	}

	public void setRoomDataModel(ListDataModel<Room> roomDataModel) {
		this.roomDataModel = roomDataModel;
	}

	public ListDataModel<Speaker> getSpeakerDataModel() {
		return speakerDataModel;
	}

	public void setSpeakerDataModel(ListDataModel<Speaker> speakerDataModel) {
		this.speakerDataModel = speakerDataModel;
	}

	public List<Speaker> getSpeakersSelected() {
		return speakersSelected;
	}

	public void setSpeakersSelected(List<Speaker> speakersSelected) {
		this.speakersSelected = speakersSelected;
	}

	public void deleteRoom() {
		if (room == null) {
			room = getRoomDataModel().getRowData();
		}
		getRooms().remove(room);
	}

	public void deleteTalk() {
		if (null == talk) {
			talk = getTalkDataModel().getRowData();
		}
		getTalks().remove(talk);
		talk = null;
		speaker = null;
		room = null;
	}

	public void deleteSpeaker() {
		if (speaker == null) {
			speaker = getSpeakerDataModel().getRowData();
		}
		getSpeakers().remove(speaker);
		speaker = null;

	}

	public List<SelectItem> getRoomsAsSelectItems() {
		return RoomConverter.toSelectItem(getRooms());
	}
}
