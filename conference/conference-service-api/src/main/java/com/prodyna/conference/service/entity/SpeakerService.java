/**
 * 
 */
package com.prodyna.conference.service.entity;

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

	Speaker findByName(String name, String firstname);

}
