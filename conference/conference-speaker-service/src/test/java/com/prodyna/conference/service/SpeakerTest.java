package com.prodyna.conference.service;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.conference.service.model.Speaker;

@RunWith(Arquillian.class)
public class SpeakerTest {

	@Deployment
	public static Archive<?> createTestArchive() {

		File[] libs = DependencyResolvers
				.use(MavenDependencyResolver.class)
				.artifact("com.prodyna.conference:conference-core:SNAPSHOT")
				.artifact(
						"com.prodyna.conference:conference-speaker-api:SNAPSHOT")
				.resolveAsFiles();

		JavaArchive archive = ShrinkWrap
				.create(JavaArchive.class, "test.jar")
				.addPackages(true, "com.prodyna.conference.service")
				.addAsResource("META-INF/persistence.xml",
						"META-INF/persistence.xml")
				.addAsResource("META-INF/beans.xml", "META-INF/beans.xml");

		System.out.println(archive.toString(Formatters.VERBOSE));
		
		EnterpriseArchive ear = ShrinkWrap
				.create(EnterpriseArchive.class, "test.ear")
				.addAsLibraries(libs).addAsLibraries(archive);
		System.out.println(ear.toString(Formatters.VERBOSE));

		return ear;

	}

	@Inject
	private SpeakerService service;

	@Before
	public void testSetup() {
		Speaker speaker = new Speaker();
		speaker.setEmail("fherling@web.de");
		speaker.setName("Herling");
		speaker.setFirstname("Frank");

		service.save(speaker);
	}

	@Test
	public void test() {

		List<Speaker> speakers = service.listAll();

		Assert.assertNotNull(speakers);
		Assert.assertTrue("No speaker found", speakers.size() == 1);

	}

}
