/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.prodyna.conference.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Default;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.conference.core.interceptor.PerfomanceMeasuring;
import com.prodyna.conference.service.MainEntityService;
import com.prodyna.conference.service.RoomService;
import com.prodyna.conference.service.model.Room;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation
@SessionScoped
@ManagedBean(name="roomController")
@PerfomanceMeasuring
@Default
public class RoomController extends AbstractViewController{
	
    private Room entry;
    
    private java.util.List<Room> entries;
    
    private DataModel<Room> model;
    
    @Inject
    private MainEntityService service;


//    @Produces
    @Named
    public Room getEntry() {
        return entry;
    }
    
    public String startView(){
    	initView();
    	return "room";
    }
    
    public void newEntry(){
    	entry = new Room();
    }

    public void loadEntry(){
    	entry = model.getRowData();
    }
    
    public void saveEntry() throws Exception {
        try {
        	log.info("save " + entry);
        	service.save(entry);
        	
        	
        	entries.clear();
        	entries.addAll(service.loadRooms());
        	
        	entry = new Room();
        	
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved!", "Successfully saved!"));
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Could not save data");
            facesContext.addMessage(null, m);
        }
    }

    @PostConstruct
    public void initView() {
        entry = new Room();
        entries = service.loadRooms();
        
        model = new ListDataModel<Room>(entries);
    }
    
    public DataModel<Room> getModel() {
        return model;
    }
}


