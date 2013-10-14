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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import com.prodyna.booking.interceptor.PerfomanceMeasuring;
import com.prodyna.booking.model.Appartment;
import com.prodyna.booking.model.Booking;

@ApplicationScoped
public class AppartmentRepository {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;
	
	@Inject
    private Validator validator;


	@PerfomanceMeasuring
	public Appartment create(Appartment appartment) {
		em.persist(appartment);
		return appartment;
	}

	@PerfomanceMeasuring
	public Appartment findById(Long id) {
		Appartment appartment = em.find(Appartment.class, id);
		log.info("Gefunden: " + appartment);
		return appartment;
	}

	@PerfomanceMeasuring
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Appartment delete(Long id) {

		Appartment appartment = em.find(Appartment.class, id);
		if (null != appartment) {
			em.remove(appartment);
		}
		return appartment;
	}

	@PerfomanceMeasuring
	public Appartment findByEmail(String email) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Appartment> criteria = cb.createQuery(Appartment.class);
		Root<Appartment> Appartment = criteria.from(Appartment.class);
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(Appartment).where(cb.equal(Appartment.get(Appartment_.name),
		// email));
		criteria.select(Appartment).where(
				cb.equal(Appartment.get("email"), email));
		return em.createQuery(criteria).getSingleResult();
	}

	@PerfomanceMeasuring
	public Appartment findMinCapacity(int capacity) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Appartment> criteria = cb.createQuery(Appartment.class);
		Root<Appartment> Appartment = criteria.from(Appartment.class);
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(Appartment).where(cb.equal(Appartment.get(Appartment_.name),
		// email));
		criteria.select(Appartment).where(
				cb.equal(Appartment.get("capacity"), capacity));
		return em.createQuery(criteria).getSingleResult();
	}

	@PerfomanceMeasuring
	public List<Appartment> findAllOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Appartment> criteria = cb.createQuery(Appartment.class);
		Root<Appartment> Appartment = criteria.from(Appartment.class);
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(Appartment).orderBy(cb.asc(Appartment.get(Appartment_.name)));
		criteria.select(Appartment).orderBy(cb.asc(Appartment.get("name")));
		return em.createQuery(criteria).getResultList();
	}
	
	private void validate(Appartment appartment) {
    	// Create a bean validator and check for issues.
        Set<ConstraintViolation<Appartment>> violations = validator.validate(appartment);

        if (!violations.isEmpty()) {
        	
        	log.log(Level.WARNING, violations.toString());
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        
	}
}
