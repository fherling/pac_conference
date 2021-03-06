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

import com.prodyna.conference.service.entity.TalkService;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.Talk;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the
 * members table.
 */

@Path("/talk")
@RequestScoped
public class TalkRESTService implements Serializable {

	private static final long serialVersionUID = 7416499113848037298L;

	@Inject
	private TalkService service;

	@Inject
	private Logger log;

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public void delete(@PathParam("id") long id) {

		Talk entity = find(id);

		service.delete(entity);

	}

	private Talk find(long id) {
		Talk entity = service.findById(id);
		if (entity == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return entity;
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Talk findById(@PathParam("id") long id) {
		Talk entity = find(id);
		return entity;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Talk> listAll() {
		return service.loadTalks();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}/room")
	@Produces(MediaType.APPLICATION_JSON)
	public Room loadRoomFor(@PathParam("id") long id) {
		Talk entity = find(id);
		Room room = service.loadRoomFor(entity);
		if (room == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return room;
	}

	@GET
	@Path("/{id:[0-9][0-9]*}/speakers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Speaker> loadSpeakersFor(@PathParam("id") long id) {
		Talk entity = find(id);
		return service.loadSpeakersFor(entity);
	}

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Talk save(Talk talk) {
		try {
			return service.save(talk);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Cannot save talk", e);
			throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
		}
	}
}
