/**
 * 
 */
package com.prodyna.conference.service;

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
import com.prodyna.conference.service.model.BusinessQueries;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.Talk;
import com.prodyna.conference.service.model.TalkRoom;
import com.prodyna.conference.service.model.TalkSpeaker;
import com.prodyna.conference.service.util.TalkComparator;

/**
 * @author fherling
 * 
 */
@Stateless
@PerfomanceMeasuring
public class TalkServiceImpl extends EntityService implements TalkService {

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
	 * com.prodyna.conference.service.MainEntityService#loadRoomsFor(com.prodyna
	 * .conference.service.model.Talk)
	 */
	@Override
	public Room loadRoomFor(Talk talk) {
		Query query = em
				.createNamedQuery(BusinessQueries.GET_ALL_TALKS_FOR_CONFERENCE);
		query.setParameter("conferenceId", talk.getId());

		try {
			Room queryResult = (Room) query.getSingleResult();
			return queryResult;
		} catch (NoResultException e) {
			log.log(Level.INFO, "No room found for" + talk);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.conference.service.MainEntityService#loadSpeakersFor(com.
	 * prodyna.conference.service.model.Talk)
	 */
	@Override
	public List<Speaker> loadSpeakersFor(Talk talk) {
		List<Speaker> result = new ArrayList<Speaker>();

		Query query = em
				.createNamedQuery(BusinessQueries.GET_ALL_SPEAKERS_FOR_TALK);
		query.setParameter("talkId", talk.getId());

		List<TalkSpeaker> queryResult = query.getResultList();

		for (TalkSpeaker talkSpeaker : queryResult) {
			result.add(talkSpeaker.getSpeaker());
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.conference.service.MainEntityService#save(com.prodyna.conference
	 * .service.model.Talk)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Talk save(Talk talk) {

		validate(talk);

		if (talk.getId() == null) {
			talk.setInsertTimestamp(new Timestamp(System.currentTimeMillis()));
			em.persist(talk);
		} else {
			talk = em.merge(talk);
		}

		em.flush();
		em.clear();

		talk = findById(talk.getId());

		fireSaveEvent(talk);

		return talk;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.conference.service.MainEntityService#delete(com.prodyna.
	 * conference.service.model.Talk)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Talk talk) {
		Talk entity = em.find(Talk.class, talk.getId());

		if (null != entity) {

			Room room = loadRoomFor(talk);
			if (null != room) {
				unassign(talk, room);
			}

			List<Speaker> speakers = loadSpeakersFor(talk);
			if (null != speakers && !speakers.isEmpty()) {
				unassign(talk, speakers);
			}

			em.remove(entity);
			em.flush();
			em.clear();

		}
	}

	@Override
	public Talk findById(long id) {
		return em.find(Talk.class, id);
	}


	@Override
	public List<Talk> loadTalks() {
		List<Talk> result = new ArrayList<Talk>();

		Query query = em.createNamedQuery(BusinessQueries.GET_ALL_TALKS);

		List<Talk> queryResult = query.getResultList();

		result.addAll(queryResult);
		
		Collections.sort(queryResult, new TalkComparator());

		return result;
	}

	@Override
	public List<Talk> loadTalksForSpeaker(Speaker speaker) {

		List<Talk> result = new ArrayList<Talk>();
		if (null != speaker && null != speaker.getId()) {
			Query query = em
					.createNamedQuery(BusinessQueries.FIND_ALL_TALKS_FOR_SPEAKER);
			query.setParameter("speakerId", speaker.getId());

			List<TalkSpeaker> queryResult = query.getResultList();

			for (TalkSpeaker talkSpeaker : queryResult) {
				result.add(talkSpeaker.getTalk());
			}
		}
		return result;

	}

	@Override
	public List<Talk> loadTalksForRoom(Room room) {
		List<Talk> result = new ArrayList<Talk>();
		if (null != room && null != room.getId()) {
			Query query = em
					.createNamedQuery(BusinessQueries.FIND_ALL_TALKS_FOR_ROOM);
			query.setParameter("roomId", room.getId());

			List<TalkRoom> queryResult = query.getResultList();

			for (TalkRoom talkRoom : queryResult) {
				result.add(talkRoom.getTalk());
			}
		}
		return result;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.conference.service.MainEntityService#save(com.prodyna.conference
	 * .service.model.Talk, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void assign(Talk talk, List<Speaker> speakers) {
	
		if (null == talk) {
			throw new NullPointerException("Parameter talk is NULL");
		}
		if (null == speakers) {
			throw new NullPointerException("Parameter speakers is NULL");
		}
	
		for (Speaker speaker : speakers) {
			assign(talk, speaker);
		}
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.conference.service.MainEntityService#save(com.prodyna.conference
	 * .service.model.Talk, com.prodyna.conference.service.model.Room)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void assign(Talk talk, Room room) {
	
		if (null == talk) {
			throw new NullPointerException("Parameter talk is NULL");
		}
		if (null == room) {
			throw new NullPointerException("Parameter room is NULL");
		}
	
		Query query = em.createNamedQuery("findTalkRoom");
		query.setParameter("talkId", talk.getId());
		query.setParameter("roomId", room.getId());
	
		TalkRoom talkRoom;
		try {
			talkRoom = (TalkRoom) query.getSingleResult();
	
		} catch (NoResultException e) {
			//No entity found, so save this one;
			talkRoom = new TalkRoom();
			talkRoom.setTalk(talk);
			talkRoom.setRoom(room);
	
			em.persist(talkRoom);
	
		}
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.conference.service.MainEntityService#save(com.prodyna.conference
	 * .service.model.Talk, com.prodyna.conference.service.model.Speaker)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void assign(Talk talk, Speaker speaker) {
	
		if (null == talk) {
			throw new NullPointerException("Parameter talk is NULL");
		}
		if (null == speaker) {
			throw new NullPointerException("Parameter speaker is NULL");
		}
	
		Query query = em.createNamedQuery("findTalkSpeaker");
		query.setParameter("talkId", talk.getId());
		query.setParameter("speakerId", speaker.getId());
		
		
		TalkSpeaker talkSpeaker;
		try {
			talkSpeaker = (TalkSpeaker) query.getSingleResult();
	
		} catch (NoResultException e) {
			//No entity found, so save this one;
			talkSpeaker = new TalkSpeaker();
			talkSpeaker.setTalk(talk);
			talkSpeaker.setSpeaker(speaker);
	
			em.persist(talkSpeaker);
	
		}
	
	}

	@Override
	public List<Talk> isAssignedTo(Room room) {
	
		Query query = em
				.createNamedQuery(BusinessQueries.IS_ROOM_ASSIGNED_TO_TALK);
	
		query.setParameter("roomId", room.getId());
	
		List<Talk> talk = new ArrayList<Talk>();
		List<TalkRoom> result = query.getResultList();
		for (TalkRoom talkRoom : result) {
			talk.add(talkRoom.getTalk());
		}
		if (talk.isEmpty()) {
			log.info(room + " is not assigned!");
		}
		return talk;
	
	}

	@Override
	public List<Talk> isAssignedTo(Speaker speaker) {
	
		Query query = em
				.createNamedQuery(BusinessQueries.IS_SPEAKER_ASSIGNED_TO_TALK);
	
		query.setParameter("speakerId", speaker.getId());
	
		List<Talk> talk = new ArrayList<Talk>();
		List<TalkSpeaker> result = query.getResultList();
		for (TalkSpeaker talkSpeaker : result) {
			talk.add(talkSpeaker.getTalk());
		}
		if (talk.isEmpty()) {
			log.info(speaker + " is not assigned!");
		}
		return talk;
	
	}

	

	@Override
	public void unassign(Talk talk, List<Speaker> speakers) {
	
		if (null == talk) {
			throw new NullPointerException("Parameter talk is NULL");
		}
		if (null == speakers) {
			throw new NullPointerException("Parameter speakers is NULL");
		}
	
		for (Speaker speaker : speakers) {
			unassign(talk, speaker);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.conference.service.MainEntityService#remove(com.prodyna.
	 * conference.service.model.Talk, com.prodyna.conference.service.model.Room)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void unassign(Talk talk, Room room) {
	
		if (null == talk) {
			throw new NullPointerException("Parameter talk is NULL");
		}
		if (null == room) {
			throw new NullPointerException("Parameter room is NULL");
		}
	
		Query delete = em
				.createNamedQuery(BusinessQueries.UNASSIGN_ROOM_FROM_TALK);
		delete.setParameter("roomId", room.getId());
		delete.setParameter("talkId", talk.getId());
	
		int result = delete.executeUpdate();
		log.info(result + " elements deleted");
		em.flush();
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.conference.service.MainEntityService#remove(com.prodyna.
	 * conference.service.model.Talk,
	 * com.prodyna.conference.service.model.Speaker)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void unassign(Talk talk, Speaker speaker) {
	
		if (null == talk) {
			throw new NullPointerException("Parameter talk is NULL");
		}
		if (null == speaker) {
			throw new NullPointerException("Parameter speaker is NULL");
		}
	
		Query delete = em
				.createNamedQuery(BusinessQueries.UNASSIGN_SPEAKER_FROM_TALK);
		delete.setParameter("speakerId", speaker.getId());
		delete.setParameter("talkId", talk.getId());
	
		int result = delete.executeUpdate();
	
		em.flush();
	
	}
}
