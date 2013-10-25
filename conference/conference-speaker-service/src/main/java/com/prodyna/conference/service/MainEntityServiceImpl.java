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
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import com.prodyna.conference.core.event.ObjectSavedEvent;
import com.prodyna.conference.core.interceptor.PerfomanceMeasuring;
import com.prodyna.conference.service.model.BaseEntity;
import com.prodyna.conference.service.model.BusinessQueries;
import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.ConferenceTalk;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.Talk;
import com.prodyna.conference.service.model.TalkRoom;
import com.prodyna.conference.service.model.TalkSpeaker;
import com.prodyna.conference.service.model.TimeRange;

/**
 * @author fherling
 * 
 */
@Stateless
@PerfomanceMeasuring
public class MainEntityServiceImpl implements MainEntityService {

	@Inject
	private EntityManager em;

	@Inject
	private Validator validator;

	@Inject
	private Logger log;

	@Inject
	private Event<ObjectSavedEvent> eventSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.conference.service.MainEntityService#loadRooms()
	 */
	@Override
	public List<Room> loadRooms() {
		List<Room> result = new ArrayList<Room>();

		Query query = em.createNamedQuery(BusinessQueries.GET_ALL_ROOMS);

		List<Room> queryResult = query.getResultList();

		result.addAll(queryResult);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.conference.service.MainEntityService#loadRoomsFor(com.prodyna
	 * .conference.service.model.Talk)
	 */
	@Override
	public Room loadRoomFor(Talk talk) {
		List<Room> result = new ArrayList<Room>();

		Query query = em
				.createNamedQuery(BusinessQueries.GET_ALL_TALKS_FOR_CONFERENCE);
		query.setParameter("conferenceId", talk.getId());

		Room queryResult = (Room) query.getSingleResult();

		return queryResult;
	}

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

		List<Speaker> queryResult = query.getResultList();

		result.addAll(queryResult);

		return result;
	}

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.conference.service.MainEntityService#save(com.prodyna.conference
	 * .service.model.Room)
	 */
	@Override
	public Room save(Room room) {

		if (room.getId() == null) {
			em.persist(room);
		} else {
			room = em.merge(room);
		}

		fireSaveEvent(room);

		return room;
	}

	private void fireSaveEvent(Object savedObject) {
		ObjectSavedEvent event = new ObjectSavedEvent();
		event.setSavedObject(savedObject);

		eventSource.fire(event);
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
			em.persist(talk);
		} else {
			talk = em.merge(talk);
		}

		em.flush();

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
	 * com.prodyna.conference.service.MainEntityService#save(com.prodyna.conference
	 * .service.model.Speaker)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Speaker save(Speaker speaker) {
		if (speaker.getId() == null) {
			em.persist(speaker);
		} else {
			speaker = em.merge(speaker);
		}

		fireSaveEvent(speaker);

		return speaker;
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

		TalkRoom talkRoom = new TalkRoom();
		talkRoom.setTalk(talk);
		talkRoom.setRoom(room);

		em.persist(talkRoom);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.conference.service.MainEntityService#save(com.prodyna.conference
	 * .service.model.Conference, com.prodyna.conference.service.model.Talk)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void assign(Conference conference, Talk talk) {

		ConferenceTalk conferenceTalk = new ConferenceTalk();
		conferenceTalk.setTalk(talk);
		conferenceTalk.setConference(conference);

		em.persist(conferenceTalk);

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
		
		TalkSpeaker talkRoom = new TalkSpeaker();
		talkRoom.setTalk(talk);
		talkRoom.setSpeaker(speaker);

		em.persist(talkRoom);

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
	public void assign(Talk talk, List<Speaker> speaker) {

		for (Speaker s : speaker) {
			assign(talk, s);
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
		// TODO Auto-generated method stub

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
			em.remove(entity);
			em.flush();
			em.clear();

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.conference.service.MainEntityService#delete(com.prodyna.
	 * conference.service.model.Room)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Room room) {
		Room entity = em.find(Room.class, room.getId());

		if (null != entity) {
			em.remove(entity);
			em.flush();
			em.clear();

		}

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
				unassign(conference, talk);
			}

			em.remove(entity);
			em.flush();
			em.clear();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.conference.service.MainEntityService#remove(com.prodyna.
	 * conference.service.model.Conference,
	 * com.prodyna.conference.service.model.Talk)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void unassign(Conference conference, Talk talk) {

		Query delete = em
				.createNamedQuery(BusinessQueries.UNASSIGN_TALK_FROM_CONFERENCE);
		delete.setParameter("conferenceId", conference.getId());
		delete.setParameter("talkId", talk.getId());

		int result = delete.executeUpdate();

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
		Query delete = em
				.createNamedQuery(BusinessQueries.UNASSIGN_SPEAKER_FROM_TALK);
		delete.setParameter("speakerId", speaker.getId());
		delete.setParameter("talkId", talk.getId());

		int result = delete.executeUpdate();

		em.flush();

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
		Query delete = em
				.createNamedQuery(BusinessQueries.UNASSIGN_ROOM_FROM_TALK);
		delete.setParameter("roomId", room.getId());
		delete.setParameter("talkId", talk.getId());

		int result = delete.executeUpdate();

		em.flush();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.conference.service.MainEntityService#isRoomAvailable(com.
	 * prodyna.conference.service.model.Room,
	 * com.prodyna.conference.service.model.TimeRange)
	 */
	@Override
	public boolean isRoomAvailable(Room room, TimeRange range) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.conference.service.MainEntityService#isSpeakerAvailable(com
	 * .prodyna.conference.service.model.Speaker,
	 * com.prodyna.conference.service.model.TimeRange)
	 */
	@Override
	public boolean isSpeakerAvailable(Speaker speaker, TimeRange range) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Room findRoomById(long id) {

		return em.find(Room.class, id);
	}

	@Override
	public Talk findTalkById(long id) {
		return em.find(Talk.class, id);
	}

	@Override
	public Conference findConferenceById(long id) {
		return em.find(Conference.class, id);
	}

	@Override
	public Speaker findSpeakerById(long id) {
		return em.find(Speaker.class, id);
	}

	

}
