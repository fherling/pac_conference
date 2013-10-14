package com.prodyna.conference.test;

import java.lang.management.ManagementFactory;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;

import org.junit.Assert;
import org.junit.Test;

import com.prodyna.booking.monitoring.Entry;
import com.prodyna.booking.monitoring.Performance;
import com.prodyna.booking.monitoring.PerformanceMXBean;

public class PerformanceTest {

	@Test
	public void test() throws Exception{
		try{
			
		
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		
		ObjectName oname = new ObjectName("com.prodyna.performance:service=Performance");
		
		server.registerMBean(new Performance(), oname);
		
		
		server.invoke(oname, "report", new Object[]{"service", "method", 10}, new String[]{String.class.getName(), String.class.getName(), long.class.getName()});
		
		
		PerformanceMXBean mbean = MBeanServerInvocationHandler.newProxyInstance(server, oname, PerformanceMXBean.class, false);
		mbean.report("service", "method", 10);
		
		List<Entry> entries =  mbean.getAll();
		
		server.unregisterMBean(oname);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
		
	}
	
}
