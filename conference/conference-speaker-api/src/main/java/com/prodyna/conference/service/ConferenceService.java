/**
 * 
 */
package com.prodyna.conference.service;

import java.util.List;

import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.ConferenceDTO;

/**
 * @author fherling
 *
 */
public interface ConferenceService {
	ConferenceDTO findById(long id);

	List<ConferenceDTO> listAll();

	ConferenceDTO save(ConferenceDTO conference);

	void delete(Long id);

	void delete(ConferenceDTO conference);
}
