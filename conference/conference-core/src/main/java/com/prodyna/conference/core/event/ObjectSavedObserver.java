package com.prodyna.conference.core.event;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;


public class ObjectSavedObserver {
	
	
	@Inject
	private Logger log;
	
	
	public void handlePerformanceEvent(@Observes ObjectSavedEvent event){
		
		log.log(Level.INFO, "Object " + event.getSavedObject().getClass().getName() + " was saved with values: " +  event.getSavedObject());
	}

}
