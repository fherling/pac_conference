/**
 * 
 */
package com.prodyna.conference.business.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.prodyna.conference.core.interceptor.PerfomanceMeasuring;
import com.prodyna.conference.service.AssignService;
import com.prodyna.conference.service.ConferenceService;
import com.prodyna.conference.service.TalkService;
import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.Talk;
import com.prodyna.conference.service.model.TimeRange;
import com.prodyna.conference.service.model.WizardDTO;

/**
 * @author fherling
 * 
 */
@Stateless
@PerfomanceMeasuring
public class BusinessServiceImpl implements BusinessService {

	@Inject
	private AssignService assignService;

	@Inject
	private ConferenceService conferenceService;

	@Inject
	private TalkService talkService;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Talk saveAndAssignTalk(Talk talk, Conference conference,
			List<Speaker> speakers, Room room) {
		talk = talkService.save(talk);
		assignService.assign(conference, talk);
		if (null != speakers) {
			assignService.assign(talk, speakers);
		}
		if (null != room) {
			assignService.assign(talk, room);
		}
		return talk;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteAndUnassignTalk(Talk talk, Conference conference) {

		assignService.unassign(conference, talk);
		talkService.delete(talk);

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
		List<Talk> talks = assignService.isAssignedTo(room);
		if (null != talks && !talks.isEmpty()) {
			for (Talk talk : talks) {
				TimeRange tr = talk.calcTimeRange();
				if (tr.getStart().before(range.getEnd())
						&& tr.getEnd().after(range.getStart())) {
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
	 * com.prodyna.conference.service.MainEntityService#isSpeakerAvailable(com
	 * .prodyna.conference.service.model.Speaker,
	 * com.prodyna.conference.service.model.TimeRange)
	 */
	@Override
	public boolean isSpeakerAvailable(Speaker speaker, TimeRange range) {

		if (null == speaker) {
			throw new NullPointerException("Parameter speaker is NULL");
		}
		if (null == range) {
			throw new NullPointerException("Parameter range is NULL");
		}

		List<Talk> talks = assignService.isAssignedTo(speaker);
		if (null != talks && !talks.isEmpty()) {
			for (Talk talk : talks) {
				System.out.println(talk);

				TimeRange tr = talk.calcTimeRange();

				System.out.println(tr);

				if (tr.getStart().before(range.getEnd())
						&& tr.getEnd().after(range.getStart())) {
					return false;
				}
			}
		}

		return true;
	}

}
