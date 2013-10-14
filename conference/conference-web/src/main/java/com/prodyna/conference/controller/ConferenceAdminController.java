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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.conference.core.interceptor.PerfomanceMeasuring;
import com.prodyna.conference.service.ConferenceService;
import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.ConferenceDTO;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation
@SessionScoped
@ManagedBean(name="conferenceAdminController")
@PerfomanceMeasuring
public class ConferenceAdminController extends AbstractViewController{
	
    private ConferenceDTO entry;
    
    private java.util.List<ConferenceDTO> entries;
    
    private DataModel<ConferenceDTO> model;
    
    @Inject
    private ConferenceService service;


//    @Produces
    @Named
    public ConferenceDTO getEntry() {
        return entry;
    }
    
    public String startView(){
    	initView();
    	return "conferenceadmin";
    }
    
    public void newEntry(){
    	entry = new ConferenceDTO(new Conference(), null);
    }

    public void loadEntry(){
    	entry = model.getRowData();
    }
    
    public void saveEntry() throws Exception {
        try {
        	log.info("save " + entry);
        	service.save(entry);
        	
        	
        	entries.clear();
        	entries.addAll(service.listAll());
        	
        	entry = new ConferenceDTO(new Conference(), null);
        	
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
    	entry = new ConferenceDTO(new Conference(), null);
        entries = service.listAll();
        
        model = new ListDataModel<ConferenceDTO>(entries);
    }
    
    public DataModel<ConferenceDTO> getModel() {
        return model;
    }

	@Override
	public void deleteEntry() throws Exception {
		service.delete(model.getRowData());
	}
}


