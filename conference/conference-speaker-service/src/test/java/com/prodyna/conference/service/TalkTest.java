package com.prodyna.conference.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
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
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.Talk;


@RunWith(Arquillian.class)
public class TalkTest {

	@Deployment
    public static Archive<?> createTestArchive() {
		
		 File[] libs =  DependencyResolvers.use(MavenDependencyResolver.class)
         .artifact("com.prodyna.conference:conference-core:SNAPSHOT")
//         .artifact("com.prodyna.conference:conference-speaker-api:SNAPSHOT")
         .resolveAsFiles();
		 
        WebArchive archive =  ShrinkWrap.create(WebArchive.class, "test.war")
        		.addAsLibraries(libs)  
        		.addPackages(true, "com.prodyna.conference.service")
//        		.addPackages(true, "com.prodyna.conference.service.model")
//                .addClasses(Booking.class, Appartment.class,AppartmentRepository.class, AppartmentRegistration.class, BookingRepository.class, BookingRegistration.class, PerfomanceMeasuring.class, PerformanceMeasuringInterceptor.class, Resources.class)
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
//                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
                // Deploy our test datasource
//                .addAsWebInfResource("test-ds.xml", "test-ds.xml");
        System.out.println(archive.toString(Formatters.VERBOSE ));
        
        return archive;
        
    }
	
	
	
	@Inject
	private ConferenceService conferenceService;
	
	@Inject
	private TalkService talkService;
	
	@Inject
	private SpeakerService speakerService;
	
	
	@Inject
	private RoomService roomService;

	@Before
	public void testSetup(){
		
		Room room = new Room();
		
		room.setCapacity(10);
		room.setName("GutenTag");
		
		room = roomService.save(room);
		
		Speaker speaker = new Speaker();
		speaker.setEmail("fherling@web.de");
		speaker.setName("Herling");
		speaker.setFirstname("Frank");

		speaker = speakerService.save(speaker);

		
		Talk talk = new Talk();
		talk.setDescription("Ein TAlk");
		talk.setDurationInMinutes(20000);
		talk.setName("DER TALK");
		
		Calendar cal = new GregorianCalendar();
		
		talk.setStart(cal.getTime());
		
		cal.add(Calendar.DAY_OF_MONTH, 1);
		talk.setEnd(cal.getTime());
		
//		talk.setSpeakers(new HashSet<Speaker>());
//		
//		talk.getSpeakers().add(speaker);
		talk.setRoom(room);

//		talk = talkService.save(talk);
		
	}
	
	@Test
	@Ignore
	public void test() {
		
//		List<Talk> talk = talkService.listAll();
		
//		Assert.assertNotNull(talk);
//		Assert.assertTrue("No talk found",  talk.size() == 1);
//		
//		Assert.assertNotNull("No speaker available", talk.get(0).getSpeakers());
//		Assert.assertNotNull("No room available", talk.get(0).getRoom());
//		
	}

}
