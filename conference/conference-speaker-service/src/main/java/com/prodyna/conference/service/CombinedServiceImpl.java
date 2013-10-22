/**
 * 
 */
package com.prodyna.conference.service;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.conference.core.interceptor.PerfomanceMeasuring;
import com.prodyna.conference.service.model.ConferenceDTO;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.SpeakersForTalk;
import com.prodyna.conference.service.model.Talk;
import com.prodyna.conference.service.model.TalkDTO;
import com.prodyna.conference.service.model.TalksForConference;

/**
 * @author fherling
 *
 */
@Stateless
@PerfomanceMeasuring
@Default
public class CombinedServiceImpl implements CombinedService {

	@Inject 
	private Logger log;
	
	@Inject
	private EntityManager em;

	
	/* (non-Javadoc)
	 * @see com.prodyna.conference.service.CombinedService#loadTalksFor(com.prodyna.conference.service.model.Conference)
	 */
	@Override
	public ConferenceDTO loadTalksFor(ConferenceDTO conference) {
	
		TalksForConference talksToConference = em.find(TalksForConference.class, conference);
		Set<TalkDTO> talks = new HashSet<TalkDTO>();
	
		if( null == talksToConference){
			//throw new RuntimeException("No talks found for conference");
			return conference;
		}
		TalkDTO dto;
		for (Talk talk : talksToConference.getTalks()) {
			dto = new TalkDTO(talk);
			talks.add(dto);
		}
		
		return conference;
	}


	/* (non-Javadoc)
	 * @see com.prodyna.conference.service.CombinedService#loadSpeakersFor(com.prodyna.conference.service.model.Talk)
	 */
	@Override
	public TalkDTO loadSpeakersFor(TalkDTO talk) {
	
		SpeakersForTalk talk2speaker = em.find(SpeakersForTalk.class, talk.getId());
		Set<Speaker> speakers = new HashSet<Speaker>();
	
		if( null == talk2speaker){
			throw new RuntimeException("No speaker for talk found");
		}
		speakers.addAll(talk2speaker.getSpeakers());
		
		talk.setSpeakers(speakers);
		
		return talk;
	}


	/* (non-Javadoc)
	 * @see com.prodyna.conference.service.CombinedService#saveTalksForConference(com.prodyna.conference.service.model.Conference, java.util.List)
	 */
	@Override
	public ConferenceDTO saveTalksForConference(ConferenceDTO conference) {
	
		TalksForConference s = em.find(TalksForConference.class, conference.getId());
		HashSet<Talk> talkList = new HashSet<Talk>();
		
		for (TalkDTO talk : conference.getTalks()) {
			talkList.add(talk.getTalk());
		}
		
		
		if (null != s) {
			s.getTalks().clear();
			s.getTalks().addAll(talkList);
			s = em.merge(s);
		} else {
			s = new TalksForConference();
			s.setId(conference.getId());
			s.setTalks(talkList);
			em.persist(s);
		}
	
		return conference;
	
	}


	/* (non-Javadoc)
	 * @see com.prodyna.conference.service.CombinedService#saveSpeakerFor(com.prodyna.conference.service.model.Talk, java.util.List)
	 */
	@Override
	public TalkDTO saveSpeakerFor(TalkDTO talk) {
	
		SpeakersForTalk s = em.find(SpeakersForTalk.class, talk.getId());
		if (null != s) {
			s.getSpeakers().clear();
			s.getSpeakers().addAll(talk.getSpeakers());
			s = em.merge(s);
		} else {
			s = new SpeakersForTalk();
			s.setId(talk.getId());
			s.setSpeakers(new HashSet<Speaker>(talk.getSpeakers()));
			em.persist(s);
		}
	
		return talk;
	
	}

}
