/**
 * 
 */
package com.prodyna.conference.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import com.prodyna.conference.core.event.ObjectSavedEvent;
import com.prodyna.conference.core.interceptor.PerfomanceMeasuring;
import com.prodyna.conference.service.model.Room;

/**
 * @author fherling
 *
 */
@Stateless
@PerfomanceMeasuring
@Default
public class RoomServiceImpl implements RoomService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Validator validator;
	
	@Inject
	private Event<ObjectSavedEvent> eventSource;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Room findById(long id) {

		Room result = em.find(Room.class, id);

		return result;

	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Room> listAll() {

		Query query = em
				.createQuery("select b from com.prodyna.conference.service.model.Room b");

		List<Room> result = query.getResultList();

		log.info("Found " + result.size() + " rooms");
		
		return new ArrayList<Room>(result);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void delete(Room room) {

		delete(room.getId());

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void delete(Long id) {

		Room result = em.find(Room.class, id);

		if (null == result) {
			throw new RuntimeException("No data found");
		}


		em.remove(result);
		
		em.flush();
		em.clear();

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Room save(Room room) {

		log.info(room.toString());
		
		validate(room);

		if (null != room.getId()) {
			room = em.merge(room);
		} else {
			em.persist(room);
		}
		
		em.flush();
		em.clear();

		ObjectSavedEvent event = new ObjectSavedEvent();
		event.setSavedObject(room);
		eventSource.fire(event);

		
		log.info("Speaker successfully persited");

		return room;
	}

	private void validate(Room room) {
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Room>> violations = validator
				.validate(room);

		if (!violations.isEmpty()) {

			
			for (ConstraintViolation<Room> constraintViolation : violations) {
				log.log(Level.WARNING,   constraintViolation.getPropertyPath().toString() + " : " +  constraintViolation.getMessage());
			}

			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}

	}
	
}
