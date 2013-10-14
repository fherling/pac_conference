package com.prodyna.conference.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.ConferenceDTO;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.Talk;
import com.prodyna.conference.service.model.TalkDTO;

@RunWith(Arquillian.class)
public class ConferenceTest {

	@Deployment
	public static Archive<?> createTestArchive() {

		File[] libs = DependencyResolvers.use(MavenDependencyResolver.class)
				.artifact("com.prodyna.conference:conference-core:SNAPSHOT")
				// .artifact("com.prodyna.conference:conference-speaker-api:SNAPSHOT")
				.resolveAsFiles();

		WebArchive archive = ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addAsLibraries(libs)
				.addPackages(true, "com.prodyna.conference.service")
				// .addPackages(true, "com.prodyna.conference.service.model")
				// .addClasses(Booking.class,
				// Appartment.class,AppartmentRepository.class,
				// AppartmentRegistration.class, BookingRepository.class,
				// BookingRegistration.class, PerfomanceMeasuring.class,
				// PerformanceMeasuringInterceptor.class, Resources.class)
				.addAsResource("META-INF/persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource("META-INF/beans.xml", "beans.xml");
		// .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		// Deploy our test datasource
		// .addAsWebInfResource("test-ds.xml", "test-ds.xml");
		System.out.println(archive.toString(Formatters.VERBOSE));

		return archive;

	}

	@Inject
	private ConferenceService conferenceService;

	@Inject
	private TalkService talkService;

	@Inject
	private RoomService roomService;

	@Inject
	private SpeakerService speakerService;

	@Before
	public void testSetup() {

		Conference conference = new Conference();
		conference.setDescription("Eine Konferenz");
		conference.setName("DIE KONFERENZ");
		conference.setStart(new Date());
		conference.setEnd(new Date());

		Room room = new Room();

		room.setCapacity(10);
		room.setName("GutenTag");

		room = roomService.save(room);

		Speaker speaker = new Speaker();
		speaker.setEmail("fherling@web.de");
		speaker.setName("Herling");
		speaker.setFirstname("Frank");

		speaker = speakerService.save(speaker);

		TalkDTO talk = new TalkDTO(new Talk());
		talk.setDescription("Ein TAlk");
		talk.setDurationInMinutes(20000);
		talk.setName("DER TALK");
		talk.setStart(new Date());
		talk.setEnd(new Date());
		talk.setDurationInMinutes(1000);
		talk.getSpeakers().add(speaker);

//		talk.setRoom(room);
//		
//		talk = talkService.save(talk);
//
//		if (conference.getTalks() == null) {
//			conference.setTalks(new HashSet<TalkDTO>());
//		}
//
//		conference.getTalks().add(talk);
//
//		conferenceService.save(conference);
	}

	@Test
	@Ignore
	public void test() {

		List<ConferenceDTO> conference = conferenceService.listAll();

		Assert.assertNotNull(conference);
		Assert.assertTrue("No conferences found", conference.size() == 1);
		
		Assert.assertNotNull("No talk available", conference.get(0).getTalks());
		Assert.assertTrue("No talk available", conference.get(0).getTalks().size() > 0);
		
		

	}

}
