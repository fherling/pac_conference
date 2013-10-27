package com.prodyna.conference.service;

import java.util.Date;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.Talk;
import com.prodyna.conference.test.AbstractServiceTest;

@RunWith(Arquillian.class)
public class ServiceTestSave extends AbstractServiceTest {

	@Inject
	private RoomService roomService;
	@Inject
	private TalkService talkService;
	@Inject
	private ConferenceService conferenceService;
	@Inject
	private SpeakerService speakerService;
	@Inject
	private AssignService assignService;


	@Before
	public void testSetup() {

	}

	@Test
	public void testSaveConference() {

		Conference conference = new Conference();
		conference.setDescription("Eine Konferenz");
		conference.setName("The conference");
		
		conference.setStart(new Date());
		conference.setEnd(new Date());

		Conference saveEntity = conferenceService.save(conference);
		Assert.assertNotNull("SavedEntity is NULL", saveEntity);
		Assert.assertNotNull("ID not available", saveEntity.getId());

	}

	@Test
	public void testSaveTalk() {
		Talk talk = new Talk();
		talk.setName("Ein Talk" + System.nanoTime());
		talk.setDescription("Ein Talk");
		talk.setDuration(120);
		talk.setStart(new Date());
		talk = talkService.save(talk);

		Assert.assertNotNull("talk is NULL", talk);
		Assert.assertNotNull("talk.id is NULL", talk.getId());

	}

	@Test
	public void testSaveTalkComplete() {
		Talk talk = new Talk();
		talk.setName("Ein Talk" + System.nanoTime());
		talk.setDescription("Ein Talk");
		talk.setDuration(120);
		talk.setStart(new Date());
		
		Speaker speaker = new Speaker();
		speaker.setName("Herling" + System.nanoTime());
		speaker.setFirstName("Frank");
		speaker.setDescription("Ein Mensch");

		talk = talkService.save(talk);

		Assert.assertNotNull("talk is NULL", talk);
		Assert.assertNotNull("talk.id is NULL", talk.getId());

		speaker = speakerService.save(speaker);

		Assert.assertNotNull("speaker is NULL", speaker);
		Assert.assertNotNull("speaker.id is NULL", speaker.getId());

		assignService.assign(talk, speaker);
	}

	@Test
	public void testSaveSpeaker() {

		Speaker speaker = new Speaker();
		speaker.setName("Herling" + System.nanoTime());
		speaker.setFirstName("Frank");
		speaker.setDescription("Ein Mensch");

		speaker = speakerService.save(speaker);

		Assert.assertNotNull("speaker is NULL", speaker);
		Assert.assertNotNull("speaker.id is NULL", speaker.getId());
	}

	@Test
	public void testSaveRoom() {

		Room room = new Room();
		room.setCapacity(10);
		room.setName("Ein Raum" + System.nanoTime());

		room = roomService.save(room);

		Assert.assertNotNull("room is NULL", room);
		Assert.assertNotNull("room.id is NULL", room.getId());
	}
}
