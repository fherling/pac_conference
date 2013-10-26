package com.prodyna.conference.core.event;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.prodyna.conference.core.jms.NotificationService;


public class ObjectSavedObserver {
	
	
	@Inject
	private Logger log;

	@Inject
	private NotificationService notificationService;
	
	public void handleEvent(@Observes ObjectSavedEvent event){
		
		log.log(Level.INFO, "Object " + event.getSavedObject().getClass().getName() + " was saved with values: " +  event.getSavedObject());

		notificationService.notify(event.getSavedObject());

	}

}
