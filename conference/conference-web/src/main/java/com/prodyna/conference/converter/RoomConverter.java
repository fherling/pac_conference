/**
 * 
 */
package com.prodyna.conference.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.conference.controller.RoomController;
import com.prodyna.conference.service.RoomService;
import com.prodyna.conference.service.model.Room;

/**
 * @author fherling
 * 
 */
@Named(value = "roomConverter")
public class RoomConverter implements Converter {

	@Inject
	private Logger log;

	@EJB
	private RoomService service;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext
	 * , javax.faces.component.UIComponent, java.lang.String)
	 */

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		if (null != value) {
//			Room room = service.findByName(value);
			Room room = service.findById(Long.parseLong(value));
			return room;
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext
	 * , javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (null != value && value instanceof Room) {

			Room room = (Room) value;

			return room.getId().toString();
		}

		return null;
	}

	
	public static List<SelectItem> toSelectItem(List<Room> rooms) {

		List<SelectItem> items = new ArrayList<SelectItem>();
		SelectItem item;
		for (Room room : rooms) {
			item = new SelectItem(room, room.getName());
			items.add(item);

		}

		return items;

	}

}
