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
import com.prodyna.conference.service.model.Conference;

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
	private Event<Conference> eventSource;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Conference findById(long id) {

		Conference result = em.find(Conference.class, id);

		return result;

	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<Conference> listAll() {

		Query query = em
				.createQuery("select b from com.prodyna.conference.service.model.Conference b");

		List<Conference> result = query.getResultList();

		log.info("Found " + result.size() + " conferences");

		return new ArrayList<Conference>(result);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void delete(Conference conference) {

		delete(conference.getId());

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void delete(Long id) {

		Conference conference = findById(id);

		em.remove(conference);
		
		em.flush();

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Conference save(Conference conference) {

		log.info(conference.toString());

		validate(conference);

		if (null != conference.getId()) {
			conference = em.merge(conference);
		} else {
			em.persist(conference);
		}

		eventSource.fire(conference);
		
		log.info("Speaker successfully persited");

		return conference;
	}

	private void validate(Conference conference) {
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Conference>> violations = validator
				.validate(conference);

		if (!violations.isEmpty()) {

			
			for (ConstraintViolation<Conference> constraintViolation : violations) {
				log.log(Level.WARNING,   constraintViolation.getPropertyPath().toString() + " : " +  constraintViolation.getMessage());
			}

			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}

	}

	
}
