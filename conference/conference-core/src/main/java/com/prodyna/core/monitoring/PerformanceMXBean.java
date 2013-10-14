/**
 * 
 */
package com.prodyna.core.monitoring;

import java.util.List;

/**
 * @author fherling
 *
 */
public interface PerformanceMXBean {
	
	void reset();
	void report(String service, String method, long time);
		long getCount();
		Entry findWorstByTime();
		Entry findWorstByAverage();
		Entry findWorstByCount();
		void dump();
		List<Entry> getAll();
		float getAverage();
		float getAverage(String service);
		float getAverage(String service, String method);
}
