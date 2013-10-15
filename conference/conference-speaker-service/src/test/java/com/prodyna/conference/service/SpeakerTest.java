package com.prodyna.conference.service;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.conference.service.model.Speaker;


@RunWith(Arquillian.class)
public class SpeakerTest {

	@Deployment
    public static Archive<?> createTestArchive() {
		
		 File[] libs =  DependencyResolvers.use(MavenDependencyResolver.class)
         .artifact("com.prodyna.conference:conference-core:SNAPSHOT")
//         .artifact("com.prodyna.conference:conference-speaker-api:SNAPSHOT")
         .resolveAsFiles();
		 
        WebArchive archive =  ShrinkWrap.create(WebArchive.class, "test.war")
        		.addAsLibraries(libs)  
        		.addPackages(true, "com.prodyna.conference.service")
//                .addClasses(Booking.class, Appartment.class,AppartmentRepository.class, AppartmentRegistration.class, BookingRepository.class, BookingRegistration.class, PerfomanceMeasuring.class, PerformanceMeasuringInterceptor.class, Resources.class)
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
//                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
                // Deploy our test datasource
//                .addAsWebInfResource("test-ds.xml", "test-ds.xml");
//        System.out.println(archive.toString(Formatters.VERBOSE ));
        
        return archive;
        
    }
	
	
	
	@Inject
	private SpeakerService service;
	
	@Before
	public void testSetup(){
		Speaker speaker = new Speaker();
		speaker.setEmail("fherling@web.de");
		speaker.setName("Herling");
		speaker.setFirstname("Frank");

		service.save(speaker);
	}
	
	@Test
	@Ignore
	public void test() {
		
		List<Speaker> speakers = service.listAll();
		
		Assert.assertNotNull(speakers);
		Assert.assertTrue("No speaker found",  speakers.size() == 1);
		
	}

}
