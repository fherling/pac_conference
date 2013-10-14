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
package com.prodyna.conference.rest;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.prodyna.conference.service.ConferenceService;
import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.ConferenceDTO;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the
 * members table.
 */

@Path("/conferences")
@RequestScoped
public class ConferenceRESTService implements Serializable {

	private static final long serialVersionUID = 7657363840447001972L;

	@Inject
	private ConferenceService service;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ConferenceDTO> listAllConferences() {
		return service.listAll();
	}

	@GET
	@Path("/find/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public ConferenceDTO lookupRoomById(@PathParam("id") long id) {
		ConferenceDTO conference = service.findById(id);
		if (conference == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return conference;
	}
}
