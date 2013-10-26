/**
 * 
 */
package com.prodyna.conference.service;

import java.util.List;

import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.Talk;

/**
 * @author fherling
 *
 */
public interface AssignService {

	void assign(Conference conference, Talk talk);

	void unassign(Conference conference, Talk talk);

	void assign(Talk talk, List<Speaker> speaker);

	void assign(Talk talk, Room room);

	void assign(Talk talk, Speaker speaker);

	void unassign(Talk talk, Room room);

	void unassign(Talk talk, Speaker speaker);

	void unassign(Talk talk, List<Speaker> speakers);

	List<Talk> isAssignedTo(Speaker speaker);

	List<Talk> isAssignedTo(Room room);

	Conference isAssignedTo(Talk talk);

}
