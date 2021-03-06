package com.prodyna.conference.service.model;

public interface BusinessQueries {

	public static final String GET_ALL_CONFERENCES = "getAllConferences";
	public static final String GET_ALL_ROOMS = "getAllRooms";
	public static final String GET_ALL_ROOM_FOR_TALK = "getRoomForTalk";
	public static final String GET_ALL_SPEAKERS = "getAllSpeakers";
	public static final String GET_ALL_SPEAKERS_FOR_TALK = "getAllSpeakersForTalk";
	public static final String GET_ALL_TALKS = "getAllTalks";
	public static final String GET_ALL_TALKS_FOR_CONFERENCE = "getAllTalksForConference";
	
	public static final String UNASSIGN_TALK_FROM_CONFERENCE = "unassignTalkFromConference";
	public static final String UNASSIGN_SPEAKER_FROM_TALK = "unassignSpeakerFromTalk";
	public static final String UNASSIGN_ROOM_FROM_TALK = "unassignRoomFromTalk";
	
	public static final String IS_TALK_ASSIGNED_TO_CONFERENCE = "istalkassignedtoconference";
	public static final String IS_SPEAKER_ASSIGNED_TO_TALK = "isspeakerassignedtotalk";
	public static final String IS_ROOM_ASSIGNED_TO_TALK = "isroomassignedtotalk";
	public static final String FIND_ROOM_BY_NAME = "findRoomByName";
	public static final String FIND_SPEAKER_BY_NAME_AND_FIRSTNAME = "findSpeakerByNameAndFirstname";
	public static final String FIND_ALL_TALKS_FOR_SPEAKER = "findalltalksforspeaker";
	public static final String FIND_ALL_TALKS_FOR_ROOM = "findalltalksforroom";	
	
	
	
}


