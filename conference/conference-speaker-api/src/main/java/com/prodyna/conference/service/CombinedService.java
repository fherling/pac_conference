package com.prodyna.conference.service;

import com.prodyna.conference.service.model.ConferenceDTO;
import com.prodyna.conference.service.model.TalkDTO;

public interface CombinedService {


	TalkDTO saveSpeakerFor(TalkDTO talk);

	ConferenceDTO saveTalksForConference(ConferenceDTO conference);

	ConferenceDTO loadTalksFor(ConferenceDTO conference);

	TalkDTO loadSpeakersFor(TalkDTO talk);

}