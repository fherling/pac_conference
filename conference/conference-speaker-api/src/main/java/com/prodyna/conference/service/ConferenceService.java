/**
 * 
 */
package com.prodyna.conference.service;

import java.util.List;

import com.prodyna.conference.service.model.Conference;

/**
 * @author fherling
 *
 */
public interface ConferenceService {
	Conference findById(long id);

	List<Conference> listAll();

	Conference save(Conference conference);

	void delete(Long id);

	void delete(Conference conference);
}
