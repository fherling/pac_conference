/**
 * 
 */
package com.prodyna.conference.service;

import java.util.List;

import com.prodyna.conference.service.model.BaseEntity;
import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.Talk;
import com.prodyna.conference.service.model.TimeRange;

/**
 * @author fherling
 * 
 */
public interface MainEntityService {

	List<Room> loadRooms();

	Room loadRoomFor(Talk talk);

	List<Talk> loadTalksFor(Conference conference);

	List<Speaker> loadSpeakers();

	List<Speaker> loadSpeakersFor(Talk talk);

	List<Conference> loadConferences();

	Conference save(Conference conference);

	Room save(Room room);

	Talk save(Talk talk);

	Speaker save(Speaker speaker);

	void assign(Talk talk, Room room);

	void assign(Conference conference, Talk talk);

	void assign(Talk talk, Speaker speaker);

	void assign(Talk talk, List<Speaker> speaker);

	void activate(Conference conference);
	
	void delete(Speaker speaker);
	
	void delete(Talk talk);
	
	void delete(Room room);
	
	void delete(Conference conference);
	
	void unassign(Conference conference, Talk talk);
	
	void unassign(Talk talk, Speaker speaker);
	
	void unassign(Talk talk, Room room);
	
	boolean isRoomAvailable(Room room, TimeRange range);
	
	boolean isSpeakerAvailable(Speaker speaker, TimeRange range);

	Room findRoomById(long id);
	Talk findTalkById(long id);
	Conference findConferenceById(long id);
	Speaker findSpeakerById(long id);
	

	
}
