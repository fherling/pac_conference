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

@RunWith(Arquillian.class)
public class ServiceTestSave extends AbstractServiceTest {

	@Inject
	private MainEntityService service;

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

		Conference saveEntity = service.save(conference);
		Assert.assertNotNull("SavedEntity is NULL", saveEntity);
		Assert.assertNotNull("ID not available", saveEntity.getId());

	}

	@Test
	public void testSaveTalk() {
		Talk talk = new Talk();
		talk.setName("Ein Talk" + System.nanoTime());
		talk.setDesription("Ein Talk");
		talk.setDuration(120);
		talk.setStart(new Date());
		talk = service.save(talk);

		Assert.assertNotNull("talk is NULL", talk);
		Assert.assertNotNull("talk.id is NULL", talk.getId());

	}

	@Test
	public void testSaveTalkComplete() {
		Talk talk = new Talk();
		talk.setName("Ein Talk" + System.nanoTime());
		talk.setDesription("Ein Talk");
		talk.setDuration(120);
		talk.setStart(new Date());
		
		Speaker speaker = new Speaker();
		speaker.setName("Herling" + System.nanoTime());
		speaker.setFirstName("Frank");
		speaker.setDescription("Ein Mensch");

		talk = service.save(talk);

		Assert.assertNotNull("talk is NULL", talk);
		Assert.assertNotNull("talk.id is NULL", talk.getId());

		speaker = service.save(speaker);

		Assert.assertNotNull("speaker is NULL", speaker);
		Assert.assertNotNull("speaker.id is NULL", speaker.getId());

		service.assign(talk, speaker);
	}

	@Test
	public void testSaveSpeaker() {

		Speaker speaker = new Speaker();
		speaker.setName("Herling" + System.nanoTime());
		speaker.setFirstName("Frank");
		speaker.setDescription("Ein Mensch");

		speaker = service.save(speaker);

		Assert.assertNotNull("speaker is NULL", speaker);
		Assert.assertNotNull("speaker.id is NULL", speaker.getId());
	}

	@Test
	public void testSaveRoom() {

		Room room = new Room();
		room.setCapacity(10);
		room.setName("Ein Raum" + System.nanoTime());

		room = service.save(room);

		Assert.assertNotNull("room is NULL", room);
		Assert.assertNotNull("room.id is NULL", room.getId());
	}
}
