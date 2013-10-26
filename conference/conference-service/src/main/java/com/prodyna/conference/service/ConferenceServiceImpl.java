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
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import com.prodyna.conference.core.interceptor.PerfomanceMeasuring;
import com.prodyna.conference.service.model.BusinessQueries;
import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.Talk;

/**
 * @author fherling
 * 
 */
@Stateless
@PerfomanceMeasuring
public class ConferenceServiceImpl extends EntityService implements ConferenceService {

	@Inject
	private EntityManager em;

	@Inject
	private Validator validator;

	@Inject
	private Logger log;
	
	@Inject
	private AssignService assignService;
	

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.conference.service.MainEntityService#loadTalksFor(com.prodyna
	 * .conference.service.model.Conference)
	 */
	@Override
	public List<Talk> loadTalksFor(Conference conference) {
		List<Talk> result = new ArrayList<Talk>();

		Query query = em
				.createNamedQuery(BusinessQueries.GET_ALL_TALKS_FOR_CONFERENCE);
		query.setParameter("conferenceId", conference.getId());

		List<Talk> queryResult = query.getResultList();

		result.addAll(queryResult);

		return result;	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.conference.service.MainEntityService#loadConferences()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<Conference> loadConferences() {

		List<Conference> result = new ArrayList<Conference>();

		Query query = em.createNamedQuery(BusinessQueries.GET_ALL_CONFERENCES);

		List<Conference> queryResult = query.getResultList();

		result.addAll(queryResult);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.conference.service.MainEntityService#save(com.prodyna.conference
	 * .service.model.Conference)
	 */
	@Override
	public Conference save(Conference conference) {

		if (conference.getId() == null) {
			em.persist(conference);
		} else {
			conference = em.merge(conference);
		}

		em.flush();
		fireSaveEvent(conference);

		return conference;
	}

	private void validate(Conference conference) {
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Conference>> violations = validator
				.validate(conference);

		if (!violations.isEmpty()) {

			for (ConstraintViolation<Conference> constraintViolation : violations) {
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
	 * com.prodyna.conference.service.MainEntityService#activate(com.prodyna
	 * .conference.service.model.Conference)
	 */
	@Override
	public void activate(Conference conference) {

		//FIXME FHERLING Noch implementiert
		throw new RuntimeException("Not implemented yet");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.conference.service.MainEntityService#delete(com.prodyna.
	 * conference.service.model.Conference)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Conference conference) {

		Conference entity = em.find(Conference.class, conference.getId());

		if (null != entity) {
			List<Talk> talks = loadTalksFor(conference);
			for (Talk talk : talks) {
				assignService.unassign(conference, talk);
			}

			em.remove(entity);
			em.flush();
			em.clear();
		}

	}

	@Override
	public Conference findById(long id) {
		return em.find(Conference.class, id);
	}

	

}
