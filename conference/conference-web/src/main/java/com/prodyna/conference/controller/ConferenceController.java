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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.ListDataModel;

import com.prodyna.conference.core.interceptor.PerfomanceMeasuring;
import com.prodyna.conference.service.model.BaseEntity;
import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Talk;

@SessionScoped
@ManagedBean(name = "conferencecontroller")
@PerfomanceMeasuring
public class ConferenceController extends AbstractViewController {

	private ListDataModel<Conference> conferenceModel;

	private List<Conference> allConferences = new ArrayList<Conference>();
	private List<Room> allRooms = new ArrayList<Room>();

	private Conference conference;
	private List<Talk> talks = new ArrayList<Talk>();

	private Room room;
	private BaseEntity talk;

	@PostConstruct
	public void initView() {

		Conference conf = new Conference();
		conf.setDescription("A conference");
		conf.setId(Long.valueOf(1));
		conf.setName("The main conference");
		conf.setEnd(new Date());
		conf.setStart(new Date());
		
		allConferences.add(conf);
		
		setConferenceModel(new ListDataModel<Conference>(allConferences));
	}

	public List<Conference> getAllConferences() {
		return allConferences;
	}

	public void setAllConferences(List<Conference> allConferences) {
		this.allConferences = allConferences;
	}

	public ListDataModel<Conference> getConferenceModel() {
		return conferenceModel;
	}

	public void setConferenceModel(ListDataModel<Conference> conferenceModel) {
		this.conferenceModel = conferenceModel;
	}

}
