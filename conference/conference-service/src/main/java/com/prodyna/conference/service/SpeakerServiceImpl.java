/**
 * 
 */
package com.prodyna.conference.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import com.prodyna.conference.core.interceptor.PerfomanceMeasuring;
import com.prodyna.conference.service.exception.AlreadyAssignedException;
import com.prodyna.conference.service.model.BusinessQueries;
import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.Talk;
import com.prodyna.conference.service.model.TimeRange;

/**
 * @author fherling
 * 
 */
@Stateless
@PerfomanceMeasuring
public class SpeakerServiceImpl extends EntityService implements SpeakerService {

	@Inject
	private EntityManager em;

	@Inject
	private Validator validator;

	@Inject
	private Logger log;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.conference.service.MainEntityService#loadSpeakers()
	 */
	@Override
	public List<Speaker> loadSpeakers() {
		List<Speaker> result = new ArrayList<Speaker>();

		Query query = em.createNamedQuery(BusinessQueries.GET_ALL_SPEAKERS);

		List<Speaker> queryResult = query.getResultList();

		result.addAll(queryResult);

		return result;
	}

	private void validate(Speaker speaker) {
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Speaker>> violations = validator
				.validate(speaker);

		if (!violations.isEmpty()) {

			for (ConstraintViolation<Speaker> constraintViolation : violations) {
				log.log(Level.WARNING, constraintViolation.getPropertyPath()
						.toString() + " : " + constraintViolation.getMessage());
			}

			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.conference.service.MainEntityService#save(com.prodyna.conference
	 * .service.model.Speaker)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Speaker save(Speaker speaker) {
		
		validate(speaker);
		
		if (speaker.getId() == null) {
			speaker.setInsertTimestamp(new Timestamp(System.currentTimeMillis()));
			em.persist(speaker);
		} else {
			speaker = em.merge(speaker);
		}
		
		em.flush();
		em.clear();
		
		speaker = findById(speaker.getId());
		

		fireSaveEvent(speaker);

		return speaker;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.conference.service.MainEntityService#delete(com.prodyna.
	 * conference.service.model.Speaker)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Speaker speaker) {

		Speaker entity = em.find(Speaker.class, speaker.getId());

		if (null != entity) {
			em.remove(entity);
			em.flush();
			em.clear();

		}
	}

	

	@Override
	public Speaker findById(long id) {
		return em.find(Speaker.class, id);
	}

	@Override
	public Speaker findByName(String name, String firstname) {
		
		Query query = em.createNamedQuery(BusinessQueries.FIND_SPEAKER_BY_NAME_AND_FIRSTNAME);
		query.setParameter("name", name);
		query.setParameter("firstname", firstname);
		
		Speaker speaker = (Speaker)query.getSingleResult();
		
		return speaker;
	}

}
