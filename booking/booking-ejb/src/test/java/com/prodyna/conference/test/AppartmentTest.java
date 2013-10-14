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
package com.prodyna.conference.test;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.booking.data.AppartmentRepository;
import com.prodyna.booking.data.BookingRepository;
import com.prodyna.booking.interceptor.PerfomanceMeasuring;
import com.prodyna.booking.interceptor.PerformanceMeasuringInterceptor;
import com.prodyna.booking.model.Appartment;
import com.prodyna.booking.model.Booking;
import com.prodyna.booking.service.AppartmentRegistration;
import com.prodyna.booking.service.BookingRegistration;
import com.prodyna.booking.util.Resources;

@RunWith(Arquillian.class)
public class AppartmentTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
        		.addPackages(true, "com.prodyna.booking")
                //.addClasses(Booking.class, Appartment.class,AppartmentRepository.class, AppartmentRegistration.class, BookingRepository.class, BookingRegistration.class, PerfomanceMeasuring.class, PerformanceMeasuringInterceptor.class, Resources.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
                // Deploy our test datasource
//                .addAsWebInfResource("test-ds.xml", "test-ds.xml");
    }

    @Inject
    BookingRegistration bookingRegistration;
    
    @Inject
    AppartmentRegistration appartmentRegistration;
    
    @Inject
    AppartmentRepository repo;
    
    @Inject 
    BookingRepository bookingRepo;

    @Inject
    Logger log;

    @Test
    public void testRegister() throws Exception {
    	
    	Appartment appartment = new Appartment();
    	appartment.setCapacity(10);
    	appartment.setName("Bruchbude");
    	
    	
    	appartmentRegistration.register(appartment);
    	
    	
        
        
        
        
    }

}
