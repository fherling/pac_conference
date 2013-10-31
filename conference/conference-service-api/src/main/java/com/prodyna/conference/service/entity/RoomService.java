package com.prodyna.conference.service.entity;

import java.util.List;

import com.prodyna.conference.service.model.Room;

public interface RoomService {

	void delete(Room room);

	Room findById(long id);
	
	Room findByName(String name);

	List<Room> loadRooms();

	Room save(Room room);

}
