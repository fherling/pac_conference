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
package com.prodyna.booking.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import com.prodyna.booking.interceptor.PerfomanceMeasuring;
import com.prodyna.booking.model.Booking;


@ApplicationScoped
public class BookingRepository {
	
	@Inject
    private Logger log;
	
    @Inject
    private EntityManager em;
    
    @Inject
    private Validator validator;

    @PerfomanceMeasuring
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Booking create(Booking booking){
    	
    	validate(booking);
    	
    	if(!isAppartmentAvailable(booking)){
    		log.info("Appartment not available during this time range");
    		throw new RuntimeException("Appartment not available during this time range");
    	}
    	
    	em.persist(booking);
    	
    	return booking;
    }
    
    private void validate(Booking booking) {
    	// Create a bean validator and check for issues.
        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        if (!violations.isEmpty()) {
        	log.log(Level.WARNING, violations.toString());
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        
	}


	public Booking findById(Long id) {
        return em.find(Booking.class, id);
    }

	@PerfomanceMeasuring
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Booking deleteById(Long id) {
        Booking booking = findById(id);
        if( null != booking){
        	em.remove(booking);
        }
        return booking;
    }

    
    public Booking findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Booking> criteria = cb.createQuery(Booking.class);
        Root<Booking> booking = criteria.from(Booking.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(Booking).where(cb.equal(Booking.get(Booking_.name), email));
        criteria.select(booking).where(cb.equal(booking.get("email"), email));
        return em.createQuery(criteria).getSingleResult();
    }

    @PerfomanceMeasuring
    public List<Booking> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Booking> criteria = cb.createQuery(Booking.class);
        Root<Booking> Booking = criteria.from(Booking.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(Booking).orderBy(cb.asc(Booking.get(Booking_.name)));
        criteria.select(Booking).orderBy(cb.asc(Booking.get("name")));
        return em.createQuery(criteria).getResultList();
    }
    
    private boolean isAppartmentAvailable(Booking booking){
    	
    	if( null == booking.getAppartment()){
    		return true;
    	}
    	Query query = em.createNamedQuery("isAvailable");
    	query.setParameter("appartmentid", booking.getAppartment().getId());
    	query.setParameter("arrival", booking.getArrival());
    	query.setParameter("departure", booking.getDeparture());
    	
    	List<Object> result = query.getResultList();
    	if( null != result && result.size() > 0){
    		
    		log.info(result.toString());
    		return false;
    	}
    	return true;
    	
    }
}
