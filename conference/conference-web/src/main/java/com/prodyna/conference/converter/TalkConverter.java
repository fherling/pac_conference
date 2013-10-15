/**
 * 
 */
package com.prodyna.conference.converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.prodyna.conference.service.TalkService;
import com.prodyna.conference.service.model.TalkDTO;

/**
 * @author fherling
 * 
 */
@Named(value = "talkConverter")
public class TalkConverter implements Converter {

	@EJB
	private TalkService service;

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

			TalkDTO Talk = service.findById(Long.valueOf(value));

			return Talk;
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

		if (null != value && value instanceof TalkDTO) {

			TalkDTO Talk = (TalkDTO) value;

			return Talk.getId().toString();
		}

		return null;
	}

}
