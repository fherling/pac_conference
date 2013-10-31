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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.prodyna.conference.core.interceptor.PerfomanceMeasuring;
import com.prodyna.conference.service.entity.SpeakerService;
import com.prodyna.conference.service.model.Speaker;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the
 * members table.
 */

@Path("/speaker")
@RequestScoped
@PerfomanceMeasuring
public class SpeakerRESTService implements Serializable {

	private static final long serialVersionUID = 7416499113848037298L;

	@Inject
	private SpeakerService service;
	
	@Inject
	private Logger log;


	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public void delete(@PathParam("id") long id) {

		Speaker entity = find(id);

		service.delete(entity);

	}

	private Speaker find(long id) {
		Speaker entity = service.findById(id);
		if (entity == null) {
			throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
		}
		return entity;
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Speaker findById(@PathParam("id") long id) {
		Speaker entity = find(id);
		return entity;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Speaker> listAll() {
		return service.loadSpeakers();
	}

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Speaker save(Speaker speaker) {
		try {
			return service.save(speaker);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Cannot save speaker", e);
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
	}

}
