/**
 * 
 */
package com.prodyna.booking.monitoring;

import java.util.List;

/**
 * @author fherling
 *
 */
public interface PerformanceMXBean {
	
	void reset();
	void report(String service, String method, long time);
		int getCount();
		Entry getWorstByTime();
		Entry getWorstByAverage();
		Entry getWorstByCount();
		void dump();
		List<Entry> getAll();
		float getAverage();
		float getAverage(String service);
		float getAverage(String service, String method);
}
