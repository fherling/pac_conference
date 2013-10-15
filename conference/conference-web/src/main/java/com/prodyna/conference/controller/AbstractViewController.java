package com.prodyna.conference.controller;

import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

public abstract class AbstractViewController {

	@Inject 
	protected Logger log;

    @Inject
    protected FacesContext facesContext;
	
	
	public AbstractViewController() {
		super();
	}
	
	public abstract String startView();
	
	public abstract void deleteEntry() throws Exception;
	
	public abstract void saveEntry() throws Exception;

	protected String getRootErrorMessage(Exception e) {
	    // Default to general error message that registration failed.
	    String errorMessage = "Service call failed. See server log for more information";
	    if (e == null) {
	        // This shouldn't happen, but return the default messages
	        return errorMessage;
	    }
	
	    // Start with the exception and recurse to find the root cause
	    Throwable t = e;
	    while (t != null) {
	        // Get the message from the Throwable class instance
	        errorMessage = t.getLocalizedMessage();
	        t = t.getCause();
	    }
	    // This is the root cause message
	    if( null == errorMessage){
	    	errorMessage = "Service call failed. See server log for more information";
	    }
	    return errorMessage;
	}

}