package com.prodyna.conference.core.monitoring;

import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.prodyna.conference.core.util.MBeanProducer;

@Singleton
@Startup
public class MonitoringStartupBean {
	@Inject
	private Logger log;

	@Inject
	private MBeanServer server;

	@PostConstruct
	public void addStartup() {
		ObjectName oname;
		try {
			oname = new ObjectName(
					MBeanProducer.PERFORMANCE_MBEAN_ONAME);
			server.registerMBean(new Performance(), oname);
			log.log(Level.INFO, "PerformanceMBean registered");
		} catch (MalformedObjectNameException e) {
			log.log(Level.SEVERE, "Startup failed", e);
		} catch (InstanceAlreadyExistsException e) {
			log.log(Level.SEVERE, "Startup failed", e);
		} catch (MBeanRegistrationException e) {
			log.log(Level.SEVERE, "Startup failed", e);
		} catch (NotCompliantMBeanException e) {
			log.log(Level.SEVERE, "Startup failed", e);
		}
	}

	@PreDestroy
	public void addShutdown() {
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		ObjectName oname;
		try {
			oname = new ObjectName(
					MBeanProducer.PERFORMANCE_MBEAN_ONAME);
			server.unregisterMBean(oname);
		} catch (MalformedObjectNameException e) {
			log.log(Level.SEVERE, "Shutdown failed", e);

		} catch (MBeanRegistrationException e) {
			log.log(Level.SEVERE, "Shutdown failed", e);

		} catch (InstanceNotFoundException e) {
			log.log(Level.SEVERE, "Shutdown failed", e);
		}
	}

}
