/**
 * 
 */
package com.prodyna.conference.service;

import java.util.List;

import com.prodyna.conference.service.model.Speaker;

/**
 * @author fherling
 *
 */
public interface SpeakerService {

	void delete(Speaker speaker);

	Speaker findById(long id);

	List<Speaker> loadSpeakers();

	Speaker save(Speaker speaker);

}
