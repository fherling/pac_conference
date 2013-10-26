/**
 * 
 */
package com.prodyna.conference.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.prodyna.conference.core.interceptor.PerfomanceMeasuring;
import com.prodyna.conference.service.model.BusinessQueries;
import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.ConferenceTalk;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.Talk;
import com.prodyna.conference.service.model.TalkRoom;
import com.prodyna.conference.service.model.TalkSpeaker;

/**
 * @author fherling
 * 
 */
@Stateless
@PerfomanceMeasuring
public class AssignServiceImpl extends EntityService implements AssignService {

	@Inject
	private EntityManager em;

	@Inject
	private Logger log;

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
	 * @see
	 * com.prodyna.conference.service.MainEntityService#save(com.prodyna.conference
	 * .service.model.Conference, com.prodyna.conference.service.model.Talk)
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

		ConferenceTalk conferenceTalk = new ConferenceTalk();
		conferenceTalk.setTalk(talk);
		conferenceTalk.setConference(conference);

		em.persist(conferenceTalk);

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

}
