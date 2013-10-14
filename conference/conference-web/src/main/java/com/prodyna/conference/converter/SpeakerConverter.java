/**
 * 
 */
package com.prodyna.conference.converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.prodyna.conference.service.SpeakerService;
import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;

/**
 * @author fherling
 * 
 */
@Named(value = "speakerConverter")
public class SpeakerConverter implements Converter {

	@EJB
	private SpeakerService service;

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

			Speaker speaker = service.findById(Long.valueOf(value));

			return speaker;
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

		if (null != value && value instanceof Speaker) {

			Speaker speaker = (Speaker) value;

			return speaker.getId().toString();
		}

		return null;
	}

}
