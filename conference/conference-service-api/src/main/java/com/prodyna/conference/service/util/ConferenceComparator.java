/**
 * 
 */
package com.prodyna.conference.service.util;

import java.util.Comparator;

import com.prodyna.conference.service.model.Conference;

/**
 * @author fherling
 * 
 */
public class ConferenceComparator implements Comparator<Conference> {

	@Override
	public int compare(Conference o1, Conference o2) {

		if (null == o1 && null == o2) {
			return 0;
		}
		if (null == o1 && null != o2) {
			return 0;
		}
		if (null != o1 && null == o2) {
			return 0;
		}
		
		if( o1.getStart().before(o2.getStart())){
			return -1;
		}else if(o1.getStart().after(o2.getStart())){
			return 1;
		}else{
			return 0;
		}

	}

}
