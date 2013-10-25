package com.prodyna.conference.core.event;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.prodyna.conference.core.monitoring.PerformanceMXBean;


public class PerformanceObserver {
	
	
	@Inject
	private Logger log;
	
	@Inject
	private PerformanceMXBean performanceMgmtBean;
	
	public void handleEvent(@Observes PerformanceEvent event){
		
		log.log(Level.INFO, "Handle performance event for : " +  event.getKey());
		performanceMgmtBean.report(event.getService(), event.getMethod(), event.getTime());
		
	}

}
