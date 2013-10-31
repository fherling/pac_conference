/**
 * 
 */
package com.prodyna.conference.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.Query;

import com.prodyna.conference.core.interceptor.PerfomanceMeasuring;
import com.prodyna.conference.service.business.BusinessService;
import com.prodyna.conference.service.entity.ConferenceService;
import com.prodyna.conference.service.entity.RoomService;
import com.prodyna.conference.service.entity.SpeakerService;
import com.prodyna.conference.service.entity.TalkService;
import com.prodyna.conference.service.exception.AlreadyAssignedException;
import com.prodyna.conference.service.model.BusinessQueries;
import com.prodyna.conference.service.model.Conference;
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
public class BusinessServiceImpl implements BusinessService {

	@Inject
	private Logger log;

	@Inject
	private TalkService talkService;

	@Inject
	private SpeakerService speakerService;

	@Inject
	private RoomService roomService;

	@Inject
	private ConferenceService conferenceService;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Talk saveAndAssignTalk(Talk talk, Conference conference,
			List<Speaker> speakers, Room room) {

		validate(talk, speakers, room, conference);

		talk = talkService.save(talk);
		conferenceService.assign(conference, talk);
		if (null != speakers) {
			talkService.assign(talk, speakers);
		}
		if (null != room) {
			talkService.assign(talk, room);
		}
		return talk;
	}

	private void validate(Talk talk, List<Speaker> speakers, Room room,
			Conference conference) {

		TimeRange cRange = conference.calcTimeRange();
		TimeRange tRange = talk.calcTimeRange();

		if (cRange.getStart().before(tRange.getStart())
				&& cRange.getEnd().after(tRange.getEnd())) {
			// Everything is fine. Talk is within the conference.
		} else {
			throw new RuntimeException("Timeconstraint");
		}

		for (Speaker speaker : speakers) {
			if (!isSpeakerAvailable(speaker, tRange, talk)) {
				throw new AlreadyAssignedException(speaker.toString());
			}
		}

		if (!isRoomAvailable(room, tRange, talk)) {
			throw new AlreadyAssignedException(room.toString());

		}

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteAndUnassignTalk(Talk talk, Conference conference) {

		conferenceService.unassign(conference, talk);
		talkService.delete(talk);

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteCompleteConference(Conference conference) {
		List<Talk> talks = conferenceService.loadTalksFor(conference);
		conferenceService.delete(conference);
		for (Talk talk : talks) {
			talkService.delete(talk);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.conference.service.entity.entity.MainEntityService#isRoomAvailable(com.
	 * prodyna.conference.service.model.Room,
	 * com.prodyna.conference.service.entity.entity.model.TimeRange)
	 */
	@Override
	public boolean isRoomAvailable(Room room, TimeRange range,
			Talk talkForRoomAssign) {
		List<Talk> talks = talkService.isAssignedTo(room);
		if (null != talks && !talks.isEmpty()) {
			for (Talk talk : talks) {
				TimeRange tr = talk.calcTimeRange();

				if (null != talkForRoomAssign
						&& (talk.getId().equals(talkForRoomAssign.getId()) || talk
								.getName().equals(talkForRoomAssign.getName()))) {
					// This is the talk you want to assign the speaker again
					continue;
				}

				if (tr.getStart().before(range.getEnd())
						&& tr.getEnd().after(range.getStart())) {
					log.info(room + " already assigned to " + talk);
					return false;
				}
			}
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.conference.service.entity.entity.MainEntityService#isSpeakerAvailable(com
	 * .prodyna.conference.service.model.Speaker,
	 * com.prodyna.conference.service.entity.entity.model.TimeRange)
	 */
	@Override
	public boolean isSpeakerAvailable(Speaker speaker, TimeRange range,
			Talk talkForSpeakerAssign) {

		if (null == speaker) {
			throw new NullPointerException("Parameter speaker is NULL");
		}
		if (null == range) {
			throw new NullPointerException("Parameter range is NULL");
		}

		List<Talk> talks = talkService.isAssignedTo(speaker);
		if (null != talks && !talks.isEmpty()) {
			for (Talk talk : talks) {

				if (null != talkForSpeakerAssign
						&& (talk.getId().equals(talkForSpeakerAssign.getId()) || talk
								.getName().equals(talk.getName()))) {
					// This is the talk you want to assign the speaker again
					continue;
				}

				TimeRange tr = talk.calcTimeRange();

				if (tr.getStart().before(range.getEnd())
						&& tr.getEnd().after(range.getStart())) {
					log.info(speaker + " already assigned to " + talk);
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public void delete(Speaker speaker) {

		List<Talk> talks = isAssignedTo(speaker);
		if (!talks.isEmpty()) {
			List<Object> objs = new ArrayList<Object>(talks);
			throw new AlreadyAssignedException(speaker, objs);
		}

		speakerService.delete(speaker);

	}

	@Override
	public void delete(Room room) {

		List<Talk> talks = isAssignedTo(room);
		if (!talks.isEmpty()) {
			List<Object> objs = new ArrayList<Object>(talks);
			throw new AlreadyAssignedException(room, objs);
		}

		roomService.delete(room);

	}

	@Override
	public List<Talk> isAssignedTo(Room room) {

		return talkService.isAssignedTo(room);

	}

	@Override
	public List<Talk> isAssignedTo(Speaker speaker) {

		return talkService.isAssignedTo(speaker);

	}

}
