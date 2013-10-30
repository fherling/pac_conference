package com.prodyna.conference.business.service;

import java.util.List;

import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.Talk;
import com.prodyna.conference.service.model.TimeRange;

public interface BusinessService {

	void deleteAndUnassignTalk(Talk talk, Conference conference);

	Talk saveAndAssignTalk(Talk talk, Conference conference,
			List<Speaker> speakers, Room room);

	boolean isSpeakerAvailable(Speaker speaker, TimeRange range, Talk talk);

	boolean isRoomAvailable(Room room, TimeRange range, Talk talkForRoomAssign);

	void deleteCompleteConference(Conference conference);

	List<Talk> isAssignedTo(Speaker speaker);

	List<Talk> isAssignedTo(Room room);

	void delete(Room room);

	void delete(Speaker speaker);
	
}
