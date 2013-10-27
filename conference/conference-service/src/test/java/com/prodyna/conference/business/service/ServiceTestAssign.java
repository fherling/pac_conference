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

import com.prodyna.conference.service.AssignService;
import com.prodyna.conference.service.SpeakerService;
import com.prodyna.conference.service.TalkService;
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
	private AssignService assignService;
	
	@Before
	public void testSetup() {

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
	
		assignService.assign(talk, speaker);
	
		
		List<Talk> talks = assignService.isAssignedTo(speaker);
		boolean found = false;
		for (Talk t : talks) {
			if( t.getId().equals(talk.getId())){
				found = true;
				break;
			}
		}
		
		Assert.assertTrue("Assign failed", found);
		
		TimeRange range = new TimeRange(talk.getStart(), 50);
		
		boolean available = businessService.isSpeakerAvailable(speaker, range);
		Assert.assertFalse("speaker is available", available);
		
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(talk.getStart());
		cal.add(Calendar.MONTH, 1);
		
		range = new TimeRange(cal.getTime(), 50);
		
		available = businessService.isSpeakerAvailable(speaker, range);
		Assert.assertTrue("speaker is not available", available);
		
		
	
	}
}
