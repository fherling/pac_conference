package com.prodyna.conference.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
public class ServiceTestLoad extends AbstractServiceTest{



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
	public void testLoadConferences() {

		Conference conference = new Conference();
		conference.setDescription("Eine Konferenz");
		conference.setName("The conference");
		
		conference.setStart(new Date());
		conference.setEnd(new Date());

		Conference saveEntity = conferenceService.save(conference);
		Assert.assertNotNull("SavedEntity is NULL", saveEntity);
		Assert.assertNotNull("ID not available", saveEntity.getId());

		
		List<Conference> conf =  conferenceService.loadConferences();
		Assert.assertNotNull(conf);
		Assert.assertTrue("No conferences found", !conf.isEmpty() );

	}
	
	@Test
	public void testLoadRooms() {
		
		

		Room room = new Room();
		room.setCapacity(10);
		room.setName("Ein Raum" + System.nanoTime());

		room = roomService.save(room);

		Assert.assertNotNull("room is NULL", room);
		Assert.assertNotNull("room.id is NULL", room.getId());
		
		List<Room> result = roomService.loadRooms();
		Assert.assertNotNull(result);
		Assert.assertTrue("No rooms found", !result.isEmpty() );
		
	}
	
	@Test
	public void testLoadSpeakers() {

		Speaker speaker = new Speaker();
		speaker.setName("Herling" + System.nanoTime());
		speaker.setFirstName("Frank");
		speaker.setDescription("Ein Mensch");

		speaker = speakerService.save(speaker);

		Assert.assertNotNull("speaker is NULL", speaker);
		Assert.assertNotNull("speaker.id is NULL", speaker.getId());
		
		List<Speaker> result = speakerService.loadSpeakers();
		Assert.assertNotNull(result);
		Assert.assertTrue("No speakers found", !result.isEmpty() );
		


	}
	
	@Test
	public void testLoadSpeakersFor() {

		Speaker speaker = new Speaker();
		speaker.setName("Herling" + System.nanoTime());
		speaker.setFirstName("Frank");
		speaker.setDescription("Ein Speaker");
		
		speaker = speakerService.save(speaker);
		
		Assert.assertNotNull("speaker is null", speaker);
		Assert.assertNotNull("speaker.id is null", speaker.getId());
		
		Talk talk = new Talk();
		talk.setName("JEE" + System.nanoTime());
		talk.setDesription("JEE Einführung");
		talk.setDuration(120);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 9);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		talk.setStart(cal.getTime());
		
		talk = talkService.save(talk);
		
		Assert.assertNotNull("talk is null", talk);
		Assert.assertNotNull("talk.id is null", talk.getId());

		
		assignService.assign(talk, speaker);
		
		
		List<Speaker> result =  talkService.loadSpeakersFor(talk);
		Assert.assertNotNull(result);
		Assert.assertTrue(!result.isEmpty());

	}

	@Test
	public void testLoadTalk() {
		
		Conference conference = new Conference();
		conference.setDescription("Eine Konferenz");
		conference.setName("The conference");
		
		conference.setStart(new Date());
		conference.setEnd(new Date());

		conference = conferenceService.save(conference);
		Assert.assertNotNull("conference is NULL", conference);
		Assert.assertNotNull("conference.id is NULL", conference.getId());
		
		
		Talk talk = new Talk();
		talk.setName("Ein Talk" + System.nanoTime());
		talk.setDesription("Ein Talk");
		talk.setDuration(120);
		talk.setStart(new Date());
		talk = talkService.save(talk);
	
		Assert.assertNotNull("talk is NULL", talk);
		Assert.assertNotNull("talk.id is NULL", talk.getId());
		
		assignService.assign(conference, talk);
		
		List<Talk> talks = conferenceService.loadTalksFor(conference);
		
		Assert.assertNotNull("talks is NULL", talks);
		Assert.assertTrue(!talks.isEmpty());
	
	}

}
