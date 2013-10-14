/**
 * 
 */
package com.prodyna.conference.service;

import java.util.List;

import com.prodyna.conference.service.model.Room;

/**
 * @author fherling
 *
 */
public interface RoomService {
	Room findById(long id);

	List<Room> listAll();

	Room save(Room room);

	void delete(Long id);

	void delete(Room room);
}
