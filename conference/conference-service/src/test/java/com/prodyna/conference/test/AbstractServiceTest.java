/**
 * 
 */
package com.prodyna.conference.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

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

		System.out.println(ear.toString(Formatters.VERBOSE));

		return ear;

	}
}
