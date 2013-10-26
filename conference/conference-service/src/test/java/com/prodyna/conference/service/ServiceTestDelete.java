package com.prodyna.conference.service;

import java.util.Date;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.conference.service.exception.AlreadyAssignedException;
import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.Talk;
import com.prodyna.conference.test.AbstractServiceTest;

@RunWith(Arquillian.class)
public class ServiceTestDelete extends AbstractServiceTest {

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
	public void testDeleteConference() {

		Conference conference = new Conference();
		conference.setDescription("Eine Konferenz");
		conference.setName("The conference");

		conference.setStart(new Date());
		conference.setEnd(new Date());

		conference = conferenceService.save(conference);
		Assert.assertNotNull("conference is NULL", conference);
		Assert.assertNotNull("ID not available", conference.getId());

		conferenceService.delete(conference);

		conference = conferenceService.findById(conference.getId());

		Assert.assertNull("Object was not deleted", conference);

	}

	@Test
	public void testDeleteTalk() {
		Talk talk = new Talk();
		talk.setName("Ein Talk" + System.nanoTime());
		talk.setDesription("Ein Talk");
		talk.setDuration(120);
		talk.setStart(new Date());
		talk = talkService.save(talk);

		Assert.assertNotNull("talk is NULL", talk);
		Assert.assertNotNull("talk.id is NULL", talk.getId());

		talkService.delete(talk);

		talk = talkService.findById(talk.getId());

		Assert.assertNull("Object was not deleted", talk);

	}

	@Test
	public void testDeleteTalkComplete() {
		Talk talk = new Talk();
		talk.setName("Ein Talk" + System.nanoTime());
		talk.setDesription("Ein Talk");
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

		talkService.delete(talk);

		talk = talkService.findById(talk.getId());

		Assert.assertNull("Object was not deleted", talk);

	}

	@Test
	public void testDeleteSpeaker() {

		Speaker speaker = new Speaker();
		speaker.setName("Herling" + System.nanoTime());
		speaker.setFirstName("Frank");
		speaker.setDescription("Ein Mensch");

		speaker = speakerService.save(speaker);

		Assert.assertNotNull("speaker is NULL", speaker);
		Assert.assertNotNull("speaker.id is NULL", speaker.getId());

		speakerService.delete(speaker);

		speaker = speakerService.findById(speaker.getId());

		Assert.assertNull("Object was not deleted", speaker);
	}

	@Test
	public void testDeleteRoom() {

		Room room = new Room();
		room.setCapacity(10);
		room.setName("Ein Raum" + System.nanoTime());

		room = roomService.save(room);

		Assert.assertNotNull("room is NULL", room);
		Assert.assertNotNull("room.id is NULL", room.getId());

		roomService.delete(room);

		room = roomService.findById(room.getId());

		Assert.assertNull("Object was not deleted", room);
	}

	@Test
	public void testDeleteAlreadyAssignedSpeaker() {
		Talk talk = new Talk();
		talk.setName("Ein Talk" + System.nanoTime());
		talk.setDesription("Ein Talk");
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

		try {
			speakerService.delete(speaker);

			Assert.fail("Should fail with an AlreadyAssignedException");

		} catch (Exception e) {

			if (e.getCause() instanceof AlreadyAssignedException) {
				// Expected result

			} else {
				e.printStackTrace();
				Assert.fail("Should fail with an AlreadyAssignedException");
			}
		}

	}
}
