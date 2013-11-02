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
import com.prodyna.conference.service.entity.ConferenceService;
import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.Talk;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the
 * members table.
 */

@Path("/conference")
@RequestScoped
public class ConferenceRESTService implements Serializable {

	private static final long serialVersionUID = 7416499113848037298L;
	
	@Inject
	private Logger log;

	@Inject
	private ConferenceService service;

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public void delete(@PathParam("id") long id) {

		Conference entity = find(id);

		service.delete(entity);

	}

	private Conference find(long id) {
		Conference entity = service.findById(id);
		if (entity == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return entity;
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Conference findById(@PathParam("id") long id) {
		Conference entity = find(id);
		return entity;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Conference> listAll() {
		return service.loadConferences();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}/talk")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Talk> loadTalksFor(@PathParam("id") long id) {
		Conference conference = find(id);
		return service.loadTalksFor(conference);
	}

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Conference save(Conference conference) {
		try {
			return service.save(conference);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Cannot save conference", e);
			throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
		}
	}

}
