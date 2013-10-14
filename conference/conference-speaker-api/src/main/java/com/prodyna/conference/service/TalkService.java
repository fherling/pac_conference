package com.prodyna.conference.service;

import java.util.List;

import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.TalkDTO;

public interface TalkService {
	TalkDTO findById(long id);

	List<TalkDTO> listAll();

	TalkDTO save(TalkDTO talk);

	void delete(Long id);

	void delete(TalkDTO talk);

	List<TalkDTO> listAllForSpeaker(Speaker speaker);
}
