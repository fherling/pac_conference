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

import com.prodyna.conference.core.interceptor.PerfomanceMeasuring;
import com.prodyna.conference.service.SpeakerService;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.SpeakerDTO;
import com.prodyna.conference.service.model.Talk;

/**
 * @author fherling
 * 
 */
@Stateless
@PerfomanceMeasuring
@Default
public class SpeakerServiceImpl implements SpeakerService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Validator validator;
	
	@Inject
	private Event<Speaker> speakerEventSrc;

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Speaker findById(long id) {

		Speaker result = em.find(Speaker.class, id);

		return result;

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Speaker> listAll() {

		Query query = em
				.createQuery("select b from com.prodyna.conference.service.model.Speaker b");

		List<Speaker> result = query.getResultList();

		log.info("Found " + result.size() + " speakers");

		return new ArrayList<Speaker>(result);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void delete(Speaker speaker) {

		delete(speaker.getId());

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void delete(Long id) {

		Speaker speaker = findById(id);

		em.remove(speaker);
		
		em.flush();
		em.clear();

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Speaker save(Speaker speaker) {

		log.info(speaker.toString());

		validate(speaker);

		if (null != speaker.getId()) {
			speaker = em.merge(speaker);
		} else {
			em.persist(speaker);
		}
		
		em.flush();
		em.clear();

		speakerEventSrc.fire(speaker);
		
		log.info("Speaker successfully persited");

		return speaker;
	}

	private void validate(Speaker speaker) {
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Speaker>> violations = validator
				.validate(speaker);

		if (!violations.isEmpty()) {

			
			for (ConstraintViolation<Speaker> constraintViolation : violations) {
				log.log(Level.WARNING,   constraintViolation.getPropertyPath().toString() + " : " +  constraintViolation.getMessage());
			}

			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}

	}
}
