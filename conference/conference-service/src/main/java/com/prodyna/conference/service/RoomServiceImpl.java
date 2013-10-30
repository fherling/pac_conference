/**
 * 
 */
package com.prodyna.conference.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import com.prodyna.conference.core.interceptor.PerfomanceMeasuring;
import com.prodyna.conference.service.model.BusinessQueries;
import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.Talk;
import com.prodyna.conference.service.model.TimeRange;

/**
 * @author fherling
 * 
 */
@Stateless
@PerfomanceMeasuring
public class RoomServiceImpl extends EntityService implements RoomService {

	@Inject
	private EntityManager em;

	@Inject
	private Validator validator;

	@Inject
	private Logger log;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.conference.service.MainEntityService#loadRooms()
	 */
	@Override
	public List<Room> loadRooms() {
		List<Room> result = new ArrayList<Room>();

		Query query = em.createNamedQuery(BusinessQueries.GET_ALL_ROOMS);

		List<Room> queryResult = query.getResultList();

		result.addAll(queryResult);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.conference.service.MainEntityService#save(com.prodyna.conference
	 * .service.model.Room)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Room save(Room room) {

		validate(room);
		
		if (room.getId() == null) {
			room.setInsertTimestamp(new Timestamp(System.currentTimeMillis()));
			em.persist(room);
		} else {
			room = em.merge(room);
		}

		em.flush();
		em.clear();
		
		room = findById(room.getId());
		
		fireSaveEvent(room);

		return room;
	}

	private void validate(Room room) {
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Room>> violations = validator
				.validate(room);

		if (!violations.isEmpty()) {

			for (ConstraintViolation<Room> constraintViolation : violations) {
				log.log(Level.WARNING, constraintViolation.getPropertyPath()
						.toString() + " : " + constraintViolation.getMessage());
			}

			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.conference.service.MainEntityService#delete(com.prodyna.
	 * conference.service.model.Room)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Room room) {
		Room entity = em.find(Room.class, room.getId());

		if (null != entity) {
			em.remove(entity);
			em.flush();
			em.clear();

		}

	}

	

	@Override
	public Room findById(long id) {

		return em.find(Room.class, id);
	}

	@Override
	public Room findByName(String name) {

		Query query = em.createNamedQuery(BusinessQueries.FIND_ROOM_BY_NAME);

		query.setParameter("name", name);
		
		Room room = (Room)query.getSingleResult();

		
		
		return room;
	}

	

}
