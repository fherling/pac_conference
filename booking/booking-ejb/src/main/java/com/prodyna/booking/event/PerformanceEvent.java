/**
 * 
 */
package com.prodyna.booking.event;


/**
 * @author fherling
 *
 */
public class PerformanceEvent{
	
	private String key;
	private String service;
	private String method;
	private long time;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	

}
