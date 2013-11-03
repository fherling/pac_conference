package com.prodyna.conference.service.monitoring;

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

import com.prodyna.conference.core.event.PerformanceObserver;
import com.prodyna.conference.core.monitoring.Performance;

@Singleton
@Startup
public class MonitoringStartupBean {
	@Inject
	private Logger log;


	@PostConstruct
	public void addStartup() {
		
		log.info("Register MBEAN");
		
		ObjectName oname;
		try {
			oname = new ObjectName(
					PerformanceObserver.PERFORMANCE_MBEAN_ONAME);
			MBeanServer server = ManagementFactory.getPlatformMBeanServer();
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
		
		log.info("Unegister MBEAN");
		
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		ObjectName oname;
		try {
			oname = new ObjectName(
					PerformanceObserver.PERFORMANCE_MBEAN_ONAME);
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
