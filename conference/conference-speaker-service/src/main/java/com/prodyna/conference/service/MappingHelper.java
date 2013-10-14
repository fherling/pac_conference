package com.prodyna.conference.service;

import java.util.HashSet;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.ConferenceDTO;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.RoomDTO;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.SpeakerDTO;
import com.prodyna.conference.service.model.Talk;
import com.prodyna.conference.service.model.TalkDTO;

public class MappingHelper {
	@Inject
	public Logger log;

	public MappingHelper() {
	}
	

	public static Talk mapFrom(TalkDTO from){
		Talk to = new Talk();
		to.setDescription(from.getDescription());
		to.setDurationInSeconds(from.getDurationInSeconds());
		to.setEnd(from.getEnd());
		to.setStart(from.getStart());
		to.setId(from.getId());
		to.setName(from.getName());
		to.setRoom(from.getRoom());
		to.setUpdateTimeStamp(from.getUpdateTimeStamp());
		
		return to;
	}
	
	public static TalkDTO mapFrom(Talk from){
		TalkDTO to = new TalkDTO();
		
		to.setDescription(from.getDescription());
		to.setDurationInSeconds(from.getDurationInSeconds());
		to.setEnd(from.getEnd());
		to.setStart(from.getStart());
		to.setId(from.getId());
		to.setName(from.getName());
		to.setRoom(from.getRoom());
		to.setUpdateTimeStamp(from.getUpdateTimeStamp());
		to.setSpeakers(new HashSet<Speaker>());
		return to;
	}
	
	public static Speaker mapFrom(SpeakerDTO from){
		Speaker to = new Speaker();
		
		return to;
	}
	
	public static SpeakerDTO mapFrom(Speaker from){
		SpeakerDTO to = new SpeakerDTO();
		
		return to;
	}
	
	public static Room mapFrom(RoomDTO from){
		Room to = new Room();
		
		return to;
	}
	
	public static RoomDTO mapFrom(Room from){
		RoomDTO to = new RoomDTO();
		
		return to;
	}

	
	
	
public static Conference mapFrom(ConferenceDTO from){
		
		Conference to = new Conference();
		to.setId(from.getId());
		to.setDescription(from.getDescription());
		to.setEnd(from.getEnd());
		to.setStart(from.getStart());
		to.setName(from.getName());
		
		to.setTalks(new HashSet<Talk>());
		for (TalkDTO dtoTalk : from.getTalks()) {
			to.getTalks().add(mapFrom(dtoTalk));
		}
		
		return to;
		
	}
	
	public static ConferenceDTO mapFrom(Conference from){
		ConferenceDTO to = new ConferenceDTO();
		to.setId(from.getId());
		to.setDescription(from.getDescription());
		to.setEnd(from.getEnd());
		to.setStart(from.getStart());
		to.setName(from.getName());
		
		
		
		return to;
	}
	
}