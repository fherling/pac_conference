package com.prodyna.booking;

import java.lang.management.ManagementFactory;

import javax.enterprise.inject.Produces;
import javax.management.MBeanServer;

public class Resources {
	
	

	@Produces
	public MBeanServer produceMBeanServer(){
		return ManagementFactory.getPlatformMBeanServer();
	}
	
	
	
}
