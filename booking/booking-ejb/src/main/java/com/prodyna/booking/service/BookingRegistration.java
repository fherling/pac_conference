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
package com.prodyna.booking.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.booking.data.AppartmentRepository;
import com.prodyna.booking.data.BookingRepository;
import com.prodyna.booking.interceptor.PerfomanceMeasuring;
import com.prodyna.booking.model.Booking;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class BookingRegistration {

	@Inject
	private Logger log;

	@Inject
	private BookingRepository repository;

	@Inject
	private Event<Booking> bookingEventSrc;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Booking register(Booking booking) throws Exception {
		log.info("Registering " + booking.getName());
		repository.create(booking);
		bookingEventSrc.fire(booking);
		
		return booking;
	}
}
