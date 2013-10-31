package com.prodyna.conference.business.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.conference.service.business.BusinessService;
import com.prodyna.conference.service.entity.RoomService;
import com.prodyna.conference.service.entity.SpeakerService;
import com.prodyna.conference.service.entity.TalkService;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.Talk;
import com.prodyna.conference.service.model.TimeRange;
import com.prodyna.conference.test.AbstractServiceTest;

@RunWith(Arquillian.class)
public class ServiceTestAssign extends AbstractServiceTest {

	@Inject
	private BusinessService businessService;

	@Inject
	private TalkService talkService;
	@Inject
	private SpeakerService speakerService;
	@Inject
	private RoomService roomService;

	@Before
	public void testSetup() {

	}

	@Test
	public void testIsRoomAvailable() {
		Talk talk = new Talk();
		talk.setName("Ein Talk" + System.nanoTime());
		talk.setDescription("Ein Talk");
		talk.setDuration(120);
		talk.setStart(new Date());
		talkService.save(talk);

		Room room = new Room();
		room.setName("HALLO" + System.nanoTime());
		room.setCapacity(100);
		roomService.save(room);

		talkService.assign(talk, room);

		List<Talk> talks = talkService.isAssignedTo(room);

		Assert.assertNotNull(talks);
		Assert.assertTrue(!talks.isEmpty());
		Assert.assertTrue(talks.size() == 1);
		Assert.assertEquals(talk.getId(), talks.get(0).getId());

		TimeRange range = new TimeRange(talk.getStart(), 50);

		boolean available = businessService.isRoomAvailable(room, range, null);
		Assert.assertFalse("room is available", available);

		Calendar cal = Calendar.getInstance();
		cal.setTime(talk.getStart());
		cal.add(Calendar.MONTH, 1);

		range = new TimeRange(cal.getTime(), 50);

		available = businessService.isRoomAvailable(room, range, talk);
		Assert.assertTrue("room is not available", available);

	}

	@Test
	public void testIsSpeakerAvailable() {
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

		talkService.assign(talk, speaker);

		List<Talk> talks = talkService.isAssignedTo(speaker);
		boolean found = false;
		for (Talk t : talks) {
			if (t.getId().equals(talk.getId())) {
				found = true;
				break;
			}
		}

		Assert.assertTrue("Assign failed", found);

		TimeRange range = new TimeRange(talk.getStart(), 50);

		boolean available = businessService.isSpeakerAvailable(speaker, range,
				null);
		Assert.assertFalse("speaker is available", available);

		Calendar cal = Calendar.getInstance();
		cal.setTime(talk.getStart());
		cal.add(Calendar.MONTH, 1);

		range = new TimeRange(cal.getTime(), 50);

		available = businessService.isSpeakerAvailable(speaker, range, talk);
		Assert.assertTrue("speaker is not available", available);

	}
}
