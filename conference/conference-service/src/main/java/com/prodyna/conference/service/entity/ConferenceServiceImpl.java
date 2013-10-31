/**
 * 
 */
package com.prodyna.conference.service.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
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
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import com.prodyna.conference.core.interceptor.PerfomanceMeasuring;
import com.prodyna.conference.service.entity.ConferenceService;
import com.prodyna.conference.service.model.BusinessQueries;
import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.ConferenceTalk;
import com.prodyna.conference.service.model.Talk;
import com.prodyna.conference.service.util.TalkComparator;

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
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.conference.service.entity.entity.MainEntityService#loadTalksFor(com.prodyna
	 * .conference.service.model.Conference)
	 */
	@Override
	public List<Talk> loadTalksFor(Conference conference) {
		List<Talk> result = new ArrayList<Talk>();

		Query query = em
				.createNamedQuery(BusinessQueries.GET_ALL_TALKS_FOR_CONFERENCE);
		query.setParameter("conferenceId", conference.getId());

		List<ConferenceTalk> queryResult = query.getResultList();

		for (ConferenceTalk conferenceTalk : queryResult) {
			result.add(conferenceTalk.getTalk());
			
		}
		Collections.sort(result, new TalkComparator());
		return result;	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.conference.service.entity.entity.MainEntityService#loadConferences()
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
	 * com.prodyna.conference.service.entity.entity.MainEntityService#save(com.prodyna.conference
	 * .service.model.Conference)
	 */
	@Override
	public Conference save(Conference conference) {

		validate(conference);
		
		if (conference.getId() == null) {
			conference.setInsertTimestamp(new Timestamp(System.currentTimeMillis()));
			em.persist(conference);
		} else {
			conference = em.merge(conference);
		}

		em.flush();
		em.clear();
		
		conference = findById(conference.getId());
		
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
	 * com.prodyna.conference.service.entity.entity.MainEntityService#activate(com.prodyna
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
	 * @see com.prodyna.conference.service.entity.entity.MainEntityService#delete(com.prodyna.
	 * conference.service.model.Conference)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Conference conference) {

		Conference entity = em.find(Conference.class, conference.getId());

		if (null != entity) {
			List<Talk> talks = loadTalksFor(conference);
			for (Talk talk : talks) {
				unassign(conference, talk);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.conference.service.entity.entity.MainEntityService#save(com.prodyna.conference
	 * .service.model.Conference, com.prodyna.conference.service.entity.entity.model.Talk)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void assign(Conference conference, Talk talk) {
	
		if (null == conference) {
			throw new NullPointerException("Parameter conference is NULL");
		}
		if (null == talk) {
			throw new NullPointerException("Parameter talk is NULL");
		}
	
		Query query = em.createNamedQuery("findConferenceTalk");
		query.setParameter("talkId", talk.getId());
		query.setParameter("conferenceId", conference.getId());
	
		ConferenceTalk ct;
		try {
			ct = (ConferenceTalk) query.getSingleResult();
	
		} catch (NoResultException e) {
			//No entity found, so save this one;
			ct = new ConferenceTalk();
			ct.setTalk(talk);
			ct.setConference(conference);
	
			em.persist(ct);
	
		}
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.conference.service.entity.entity.MainEntityService#remove(com.prodyna.
	 * conference.service.model.Conference,
	 * com.prodyna.conference.service.entity.entity.model.Talk)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void unassign(Conference conference, Talk talk) {
	
		if (null == conference) {
			throw new NullPointerException("Parameter conference is NULL");
		}
		if (null == talk) {
			throw new NullPointerException("Parameter talk is NULL");
		}
	
		Query delete = em
				.createNamedQuery(BusinessQueries.UNASSIGN_TALK_FROM_CONFERENCE);
		delete.setParameter("conferenceId", conference.getId());
		delete.setParameter("talkId", talk.getId());
	
		int result = delete.executeUpdate();
		log.info(result + " elements deleted");
	
		em.flush();
	
	}

	@Override
	public Conference isAssignedTo(Talk talk) {
	
		Query query = em
				.createNamedQuery(BusinessQueries.IS_TALK_ASSIGNED_TO_CONFERENCE);
	
		query.setParameter("talkId", talk.getId());
	
		Conference conference = null;
		try {
			ConferenceTalk conferenceTalk = (ConferenceTalk) query
					.getSingleResult();
	
			conference = conferenceTalk.getConference();
		} catch (NoResultException e) {
			log.info(talk + " is not assigned!");
		}
		return conference;
	
	}	

}
