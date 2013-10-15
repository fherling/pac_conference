/**
 * 
 */
package com.prodyna.conference.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import com.prodyna.conference.core.event.ObjectSavedEvent;
import com.prodyna.conference.core.interceptor.PerfomanceMeasuring;
import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.ConferenceDTO;
import com.prodyna.conference.service.model.Talk;
import com.prodyna.conference.service.model.TalkDTO;
import com.prodyna.conference.service.model.TalksForConference;

/**
 * @author fherling
 *
 */
@Stateless
@PerfomanceMeasuring
@Default
public class ConferenceServiceImpl implements ConferenceService {

	@Inject 
	private Logger log;
	
	@Inject
	private EntityManager em;

	@Inject
	private Validator validator;
	
	@Inject
	private Event<ObjectSavedEvent> eventSource;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public ConferenceDTO findById(long id) {

		Conference result = em.find(Conference.class, id);

		Set<TalkDTO> pTalks = loadTalksForConference(id);
		
		ConferenceDTO dto = new ConferenceDTO(result, pTalks); 
		
		return dto;

	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<ConferenceDTO> listAll() {

		Query query = em
				.createQuery("select b from com.prodyna.conference.service.model.Conference b");

		List<ConferenceDTO> dtoResult = new ArrayList<ConferenceDTO>();
		
		ConferenceDTO dto;
		Set<TalkDTO> talks = null;
		
		List<Conference> result = query.getResultList();
		
		for (Conference conference : result) {
			talks = null;
			dto = new ConferenceDTO(conference, talks);
			dto.getTalks().addAll(loadTalksForConference(conference.getId()));
			dtoResult.add(dto);
		}
		

		log.info("Found " + dtoResult.size() + " conferences");

		return dtoResult;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void delete(ConferenceDTO conference) {

		delete(conference.getId());

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void delete(Long id) {

		ConferenceDTO conference = findById(id);

		em.remove(conference.getConference());
		
		
		TalksForConference s = em.find(TalksForConference.class, conference.getId());
		
		if( null != s){
			em.remove(s);
		}
		
		em.flush();

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public ConferenceDTO save(ConferenceDTO conference) {

		log.info(conference.toString());

		Conference entity = conference.getConference();
		
		validate(conference);

		if (null != entity.getId()) {
			entity = em.merge(entity);
		} else {
			em.persist(entity);
		}
		
		//PERSIST TALK TO CONFERENCE
		em.flush();
		
		conference.setConference(entity);
		
		saveTalksForConference(conference);
		
		ObjectSavedEvent event = new ObjectSavedEvent();
		event.setSavedObject(conference);
		eventSource.fire(event);
		
		log.info("Conference successfully persited");

		return conference;
	}

	private void validate(ConferenceDTO conference) {
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Conference>> violations = validator
				.validate(conference.getConference());

		if (!violations.isEmpty()) {

			
			for (ConstraintViolation<Conference> constraintViolation : violations) {
				log.log(Level.WARNING,   constraintViolation.getPropertyPath().toString() + " : " +  constraintViolation.getMessage());
			}

			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}

		
		
	}

	private Set<TalkDTO> loadTalksForConference(Long id) {
	
		TalksForConference talksToConference = em.find(TalksForConference.class, id);
		Set<TalkDTO> talks = new HashSet<TalkDTO>();
	
		if( null == talksToConference){
			//throw new RuntimeException("No talks found for conference");
			return talks;
		}
		TalkDTO dto;
		for (Talk talk : talksToConference.getTalks()) {
			dto = new TalkDTO(talk);
			talks.add(dto);
		}
		
		return talks;
	}

	private TalksForConference saveTalksForConference(ConferenceDTO conference) {
	
		TalksForConference s = em.find(TalksForConference.class, conference.getId());
		HashSet<Talk> talks = new HashSet<Talk>();
		
		for (TalkDTO talk : conference.getTalks()) {
			talks.add(talk.getTalk());
		}
		
		
		if (null != s) {
			s.getTalks().clear();
			s.getTalks().addAll(talks);
			s = em.merge(s);
		} else {
			s = new TalksForConference();
			s.setId(conference.getId());
			s.setTalks(talks);
			em.persist(s);
		}
	
		return s;
	
	}

	
}
