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
import com.prodyna.conference.service.TalkService;
import com.prodyna.conference.service.model.Talk;
import com.prodyna.conference.service.model.TalkDTO;

@SessionScoped
@ManagedBean(name="talkController")
@PerfomanceMeasuring
public class TalkController extends AbstractViewController  {
	
	

    private TalkDTO entry;
    
    private java.util.List<TalkDTO> talks;
    
    private DataModel<TalkDTO> model;
    
    @Inject
    private TalkService service;
    


//    @Produces
    @Named
    public TalkDTO getEntry() {
        return entry;
    }
    
    public String startView(){
    	initView();
    	return "talk";
    }
    
    public void newEntry(){
    	entry = new TalkDTO(new Talk());
    }

    public void loadEntry(){
    	entry = model.getRowData();
    	entry = service.findById(entry.getId());
    }
    
    public void saveEntry() throws Exception {
        try {
        	log.info("save " + entry);
        	entry = service.save(entry);
        	entry = new TalkDTO(new Talk());
        	
        	
        	talks.clear();
        	talks.addAll(service.listAll());
        	
        	
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
    	entry = new TalkDTO(new Talk());
        talks = service.listAll();
        
        model = new ListDataModel<TalkDTO>(talks);
    }
    
    public DataModel<TalkDTO> getModel() {
        return model;
    }
    
    
    @Override
	public void deleteEntry() throws Exception {
		service.delete(model.getRowData());
		entry = new TalkDTO(new Talk());
    	
    	
    	talks.clear();
    	talks.addAll(service.listAll());
	}
    
    
}


