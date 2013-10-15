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

	Speaker findById(long id);

	List<Speaker> listAll();

	Speaker save(Speaker speaker);

	void delete(Long id);

	void delete(Speaker speaker);

}
