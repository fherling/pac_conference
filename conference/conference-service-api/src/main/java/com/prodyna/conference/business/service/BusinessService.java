package com.prodyna.conference.business.service;

import java.util.List;

import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.Talk;
import com.prodyna.conference.service.model.TimeRange;
import com.prodyna.conference.service.model.WizardDTO;

public interface BusinessService {

	boolean isRoomAvailable(Room room, TimeRange range);

	boolean isSpeakerAvailable(Speaker speaker, TimeRange range);

	void deleteAndUnassignTalk(Talk talk, Conference conference);

	Talk saveAndAssignTalk(Talk talk, Conference conference,
			List<Speaker> speakers, Room room);
	
}
