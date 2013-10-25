package com.prodyna.conference.core.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
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

@Alternative
public class MBeanProducer {
	
	public static final String PERFORMANCE_MBEAN_ONAME = "com.prodyna.performance:service=Performance";

	@Inject
	private Logger log;
	
	@Inject
	private MBeanServer server;
	
	@Produces
	public PerformanceMXBean producePerformanceMgmtBean() throws MalformedObjectNameException{
		
		log.log(Level.INFO, "produce PerformanceMgmtBean");

		ObjectName oname = new ObjectName(PERFORMANCE_MBEAN_ONAME);
		if( !server.isRegistered(oname)){
			register();
		}
		PerformanceMXBean mbean = MBeanServerInvocationHandler.newProxyInstance(server, oname, PerformanceMXBean.class, false);
		return mbean;
	}
	
	
	private void register(){
		ObjectName oname;
		try {
			oname = new ObjectName(
					PERFORMANCE_MBEAN_ONAME);
			server.registerMBean(new Performance(), oname);
			log.log(Level.INFO, "PerformanceMBean registered");
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		} catch (InstanceAlreadyExistsException e) {
			e.printStackTrace();
		} catch (MBeanRegistrationException e) {
			e.printStackTrace();
		} catch (NotCompliantMBeanException e) {
			e.printStackTrace();
		}
	}
	
	
}
