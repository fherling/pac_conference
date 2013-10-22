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
import com.prodyna.conference.service.SpeakerService;
import com.prodyna.conference.service.model.Speaker;

@SessionScoped
@ManagedBean(name="speakerController")
@PerfomanceMeasuring
public class SpeakerController extends AbstractViewController{
	

    private Speaker entry;
    
    private java.util.List<Speaker> entries;
    
    private DataModel<Speaker> model;
    
    @Inject
    private SpeakerService service;



    @Named
    public Speaker getEntry() {
        return entry;
    }
    
    public String startView(){
    	initView();
    	return "speaker";
    }
    
    public void newEntry(){
    	entry = new Speaker();
    }

    public void loadEntry(){
    	entry = model.getRowData();
    	log.info("load " + entry);
    }
    
    public void saveEntry() throws Exception {
        try {
        	log.info("save " + entry);
        	service.save(entry);
        	
        	
        	entries.clear();
        	entries.addAll(service.listAll());
        	
        	entry = new Speaker();
        	
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
        entry = new Speaker();
        entries = service.listAll();
        
        model = new ListDataModel<Speaker>(entries);
    }
    
    public DataModel<Speaker> getModel() {
        return model;
    }
    
    @Override
	public void deleteEntry() throws Exception {
		service.delete(model.getRowData());
		entry = new Speaker();
		entries.clear();
        entries.addAll(service.listAll());
	}
}


