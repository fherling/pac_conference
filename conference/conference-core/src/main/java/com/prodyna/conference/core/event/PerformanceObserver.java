package com.prodyna.conference.core.event;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import com.prodyna.core.monitoring.PerformanceMXBean;


public class PerformanceObserver {
	
	
	@Inject
	private Logger log;
	
	@Inject
	private PerformanceMXBean performanceMgmtBean;
	
	public void handlePerformanceEvent(@Observes PerformanceEvent event){
		
		log.log(Level.INFO, "Handle performance event for : " +  event.getKey());
		
		performanceMgmtBean.report(event.getService(), event.getMethod(), event.getTime());
		
	}

}
