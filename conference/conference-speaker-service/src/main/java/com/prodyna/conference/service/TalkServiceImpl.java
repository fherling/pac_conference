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

import javax.ejb.EJB;
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
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.SpeakersForTalk;
import com.prodyna.conference.service.model.Talk;
import com.prodyna.conference.service.model.TalkDTO;

/**
 * @author fherling
 * 
 */
@Stateless
@PerfomanceMeasuring
@Default
public class TalkServiceImpl implements TalkService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Validator validator;

	@Inject
	private Event<ObjectSavedEvent> eventSource;

	@EJB
	private SpeakerService speakerService;

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public TalkDTO findById(long id) {

		Talk result = em.find(Talk.class, id);

		if (null == result) {
			return null;
		}

		TalkDTO dto = new TalkDTO(result);

		dto.getSpeakers().addAll(loadSpeakersForTalk(dto.getId()));

		return dto;

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<TalkDTO> listAll() {

		Query query = em
				.createQuery("select b from com.prodyna.conference.service.model.Talk b");

		List<Talk> result = query.getResultList();

		List<TalkDTO> talks = new ArrayList<TalkDTO>();

		for (Talk talk : result) {
			TalkDTO dto = new TalkDTO(talk);
			dto.getSpeakers().addAll(loadSpeakersForTalk(dto.getId()));
			talks.add(dto);
		}
		//
		log.info("Found " + talks.size() + " talks");

		return talks;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void delete(TalkDTO talk) {

		delete(talk.getId());

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Long id) {

		Talk talk = em.find(Talk.class, id);

		if (null == talk) {
			throw new RuntimeException("No data found");
		}

		em.remove(talk);
		
		SpeakersForTalk s = em.find(SpeakersForTalk.class, talk.getId());
		
		if( null != s){
			em.remove(s);
		}

		em.flush();
		em.clear();

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public TalkDTO save(TalkDTO talk) {

		log.info(talk.toString());

		Talk entity = talk.getTalk();
		
		validate(entity);

		if (null != entity.getId()) {
			entity = em.merge(entity);

		} else {
			em.persist(entity);
		}


		// em.detach(talk);
		em.flush();
		em.clear();

		talk.setTalk(entity);
		
		SpeakersForTalk sft = saveSpeakerRelation(talk);
		
		talk.getSpeakers().clear();
		talk.getSpeakers().addAll(sft.getSpeakers());

		ObjectSavedEvent event = new ObjectSavedEvent();
		event.setSavedObject(talk);
		eventSource.fire(event);


		log.info("Talk successfully persited");

		return talk;
	}

	private SpeakersForTalk saveSpeakerRelation(TalkDTO talk) {

		SpeakersForTalk s = em.find(SpeakersForTalk.class, talk.getId());
		if (null != s) {
			s.getSpeakers().clear();
			s.getSpeakers().addAll(talk.getSpeakers());
			s = em.merge(s);
		} else {
			s = new SpeakersForTalk();
			s.setId(talk.getId());
			s.setSpeakers(talk.getSpeakers());
			em.persist(s);
		}

		return s;

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<TalkDTO> listAllForSpeaker(Speaker speaker) {

		Query query = em
				.createQuery("select b from com.prodyna.conference.service.model.Talk b where :speakerParam member of b.speaker");

		query.setParameter("speakerParam", speaker);

		List<Talk> result = query.getResultList();
		List<TalkDTO> talks = new ArrayList<TalkDTO>();

		TalkDTO dto;
		
		for (Talk talk : result) {
			dto = new TalkDTO(talk);
			dto.getSpeakers().addAll(loadSpeakersForTalk(dto.getId()));
			talks.add(dto);
		}

		log.info("Found " + talks.size() + " talks");

		return talks;

	}

	private void validate(Talk talk) {
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Talk>> violations = validator.validate(talk);

		if (!violations.isEmpty()) {

			for (ConstraintViolation<Talk> constraintViolation : violations) {
				log.log(Level.WARNING, constraintViolation.getPropertyPath()
						.toString() + " : " + constraintViolation.getMessage());
			}

			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}

	}

	private List<Speaker> loadSpeakersForTalk(Long id) {

		SpeakersForTalk talk2speaker = em.find(SpeakersForTalk.class, id);
		List<Speaker> speakers = new ArrayList<>();

		if( null == talk2speaker){
			throw new RuntimeException("No speaker for talk found");
		}
		speakers.addAll(talk2speaker.getSpeakers());
		return speakers;
	}

}
