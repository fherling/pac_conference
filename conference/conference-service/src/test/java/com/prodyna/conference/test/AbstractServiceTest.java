/**
 * 
 */
package com.prodyna.conference.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.runner.RunWith;

/**
 * @author fherling
 * 
 */

public class AbstractServiceTest {
	@Deployment
	public static EnterpriseArchive createTestArchive() {

		JavaArchive archive = ShrinkWrap
				.create(JavaArchive.class, "test.jar")
				.addPackages(true, "com.prodyna")
				.addAsResource("META-INF/persistence.xml",
						"META-INF/persistence.xml")
				.addAsResource("META-INF/orm.xml", "META-INF/orm.xml")
				.addAsResource("META-INF/beans.xml", "META-INF/beans.xml");

		System.out.println(archive.toString(Formatters.VERBOSE));

		EnterpriseArchive ear = ShrinkWrap
				.create(EnterpriseArchive.class, "test.ear")
				// .addAsLibraries(libs)
				.addAsResource(EmptyAsset.INSTANCE, "META-INF/application.xml")
				.addAsModule(archive);

		List<String> artifacts = new ArrayList<String>();
		artifacts.add("com.google.code.gson:gson:1.7.1");
		artifacts.add("joda-time:joda-time:2.1");

		addLib(ear, artifacts);

		System.out.println(ear.toString(Formatters.VERBOSE));

		return ear;

	}

	private static void addLib(EnterpriseArchive ear, List<String> artifacts) {
		MavenDependencyResolver resolver = DependencyResolvers.use(
				MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");

		for (String artifact : artifacts) {
			File[] files = resolver.artifact(artifact).resolveAsFiles();
			
			for (File file : files) {
				ear.addAsLibrary(file);
			}
		}

	}
}
