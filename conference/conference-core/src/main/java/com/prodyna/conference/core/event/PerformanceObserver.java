package com.prodyna.conference.core.event;

import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.prodyna.conference.core.monitoring.Performance;
import com.prodyna.conference.core.monitoring.PerformanceMXBean;

public class PerformanceObserver {
	public static final String PERFORMANCE_MBEAN_ONAME = "com.prodyna.performance:service=Performance";

	@Inject
	private Logger log;

	private PerformanceMXBean performanceMgmtBean;

	public void handleEvent(@Observes PerformanceEvent event) {

		log.log(Level.INFO, "Handle performance event for : " + event.getKey());
		getPerformanceMXBean().report(event.getService(), event.getMethod(),
				event.getTime());

	}

	private PerformanceMXBean getPerformanceMXBean() {
		if (null == performanceMgmtBean) {
			ObjectName oname;
			try {
				oname = new ObjectName(PERFORMANCE_MBEAN_ONAME);
				MBeanServer server = ManagementFactory.getPlatformMBeanServer();

				if (!server.isRegistered(oname)) {
					server.registerMBean(new Performance(), oname);
				}

				performanceMgmtBean = MBeanServerInvocationHandler
						.newProxyInstance(server, oname,
								PerformanceMXBean.class, false);
			} catch (MalformedObjectNameException e) {
				throw new RuntimeException("Cannt not find PerformanceMXBean",
						e);
			} catch (InstanceAlreadyExistsException e) {
				throw new RuntimeException("Cannt not find PerformanceMXBean",
						e);
			} catch (MBeanRegistrationException e) {
				throw new RuntimeException("Cannt not find PerformanceMXBean",
						e);
			} catch (NotCompliantMBeanException e) {
				throw new RuntimeException("Cannt not find PerformanceMXBean",
						e);
			}
		}
		return performanceMgmtBean;

	}

}
