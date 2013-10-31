/**
 * 
 */
package com.prodyna.conference.service.entity;

import java.util.List;

import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.Talk;

/**
 * @author fherling
 *
 */
public interface TalkService {

	List<Talk> loadTalks();
	
	void delete(Talk talk);

	Talk findById(long id);

	Room loadRoomFor(Talk talk);

	List<Speaker> loadSpeakersFor(Talk talk);

	Talk save(Talk talk);

	List<Talk> loadTalksForSpeaker(Speaker speaker);

	List<Talk> loadTalksForRoom(Room room);

	void assign(Talk talk, Speaker speaker);

	void unassign(Talk talk, Room room);

	void unassign(Talk talk, Speaker speaker);

	void unassign(Talk talk, List<Speaker> speakers);

	List<Talk> isAssignedTo(Speaker speaker);

	List<Talk> isAssignedTo(Room room);

	void assign(Talk talk, Room room);

	void assign(Talk talk, List<Speaker> speakers);


}
