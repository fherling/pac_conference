/**
 * 
 */
package com.prodyna.conference.service.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author fherling
 *
 */
public class TimeRange {

	private Date start;
	private Date end;
	
	
	/**
	 * @param pStart Starttime and -date
	 * @param pEnd Endtime and -date
	 */
	public TimeRange(Date pStart, Date pEnd){
		
		if( null == pStart){
			throw new NullPointerException("pStart is NULL");
		}
		
		if( null == pEnd){
			throw new NullPointerException("pEnd is NULL");
		}
		
		this.start = pStart;
		this.end = pEnd;
	}
	
	
	/**
	 * @param pStart Starttime and -date
	 * @param durationInMinutes Duration in Minutes
	 */
	public TimeRange(Date pStart, long durationInMinutes){
		if( null == pStart){
			throw new NullPointerException("pStart is NULL");
		}
		
		if( durationInMinutes < 0){
			throw new RuntimeException("durationInMinutes < 0");
		}
		

		this.start = pStart;
		
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(pStart);
		cal.add(Calendar.MINUTE, (int)durationInMinutes);
		
		this.end = cal.getTime();
		
	}


	public Date getStart() {
		return start;
	}


	public void setStart(Date start) {
		this.start = start;
	}


	public Date getEnd() {
		return end;
	}


	public void setEnd(Date end) {
		this.end = end;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeRange other = (TimeRange) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "TimeRange [start=" + start + ", end=" + end + "]";
	}
	
}

