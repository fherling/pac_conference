package com.prodyna.conference.service;

import java.util.List;

import com.prodyna.conference.service.model.Room;

public interface RoomService {

	void delete(Room room);

	Room findById(long id);

	List<Room> loadRooms();

	Room save(Room room);

}
