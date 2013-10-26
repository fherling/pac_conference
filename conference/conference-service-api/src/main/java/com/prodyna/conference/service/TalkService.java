/**
 * 
 */
package com.prodyna.conference.service;

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

}
