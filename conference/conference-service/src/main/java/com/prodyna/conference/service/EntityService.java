package com.prodyna.conference.service;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.prodyna.conference.core.event.ObjectSavedEvent;

public abstract class EntityService {

	@Inject Event<ObjectSavedEvent> eventSource;
	
	
	protected void fireSaveEvent(Object savedObject) {
		ObjectSavedEvent event = new ObjectSavedEvent();
		event.setSavedObject(savedObject);
	
		eventSource.fire(event);
	}

}