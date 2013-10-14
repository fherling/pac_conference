/**
 * 
 */
package com.prodyna.conference.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.prodyna.conference.core.interceptor.PerfomanceMeasuring;

/**
 * @author fherling
 *
 */
@SessionScoped
@ManagedBean(name="conferenceViewController")
@PerfomanceMeasuring
public class ConferenceViewController extends ConferenceAdminController {

	
	public String startView(){
    	initView();
    	return "conferenceview";
    }
	
	@Override
	public void deleteEntry() throws Exception {
		throw new RuntimeException("FUNCTION NOT AVAILABLE");
	}
}
