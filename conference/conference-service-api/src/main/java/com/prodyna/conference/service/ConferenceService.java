/**
 * 
 */
package com.prodyna.conference.service;

import java.util.List;

import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.Talk;

/**
 * @author fherling
 *
 */
public interface ConferenceService {

	void activate(Conference conference);

	void delete(Conference conference);

	Conference findById(long id);

	List<Conference> loadConferences();

	List<Talk> loadTalksFor(Conference conference);

	Conference save(Conference conference);

}
