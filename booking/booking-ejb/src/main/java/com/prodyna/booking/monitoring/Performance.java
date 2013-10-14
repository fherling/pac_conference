/**
 * 
 */
package com.prodyna.booking.monitoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author fherling
 * 
 */
public class Performance implements PerformanceMXBean {

	private long count;

	private HashMap<String, Entry> entryMap = new HashMap<String, Entry>();

	private float sum;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.booking.monitoring.PerformanceMBean#reset()
	 */
	@Override
	public synchronized void reset() {
		entryMap.clear();
		count = 0;
		sum = 0;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.booking.monitoring.PerformanceMBean#report(java.lang.String,
	 * java.lang.String, long)
	 */
	@Override
	public synchronized void report(String service, String method, long time) {

		String key = service + ":" + method;

		Entry entry = entryMap.get(key);
		if (null == entry) {
			entry = new Entry(service, method);
			entryMap.put(key, entry);
		}
		entry.report(time);
		sum += time;
		count++;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.booking.monitoring.PerformanceMBean#getCount()
	 */
	@Override
	public int getCount() {
		return entryMap.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.booking.monitoring.PerformanceMBean#getWorstByTime()
	 */
	@Override
	public synchronized Entry getWorstByTime() {

		Entry worstEntry = null;
		Set<String> keys = entryMap.keySet();
		for (String key : keys) {
			if (key.startsWith(key)) {
				Entry entry = entryMap.get(key);
				if (null == entry) {
					worstEntry = entry;
				} else {
					if (worstEntry.getSum() < entry.getSum()) {
						worstEntry = entry;
					}
				}
			}
		}
		return worstEntry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.booking.monitoring.PerformanceMBean#getWorstByAverage()
	 */
	@Override
	public synchronized Entry getWorstByAverage() {

		Entry worstEntry = null;
		Set<String> keys = entryMap.keySet();
		for (String key : keys) {
			if (key.startsWith(key)) {
				Entry entry = entryMap.get(key);
				if (null == entry) {
					worstEntry = entry;
				} else {
					if (worstEntry.getAverage() < entry.getAverage()) {
						worstEntry = entry;
					}
				}
			}
		}

		return worstEntry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.booking.monitoring.PerformanceMBean#getWorstByCount()
	 */
	@Override
	public synchronized Entry getWorstByCount() {
		Entry worstEntry = null;
		Set<String> keys = entryMap.keySet();
		for (String key : keys) {
			if (key.startsWith(key)) {
				Entry entry = entryMap.get(key);
				if (null == entry) {
					worstEntry = entry;
				} else {
					if (worstEntry.getCount() < entry.getCount()) {
						worstEntry = entry;
					}
				}
			}
		}
		return worstEntry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.booking.monitoring.PerformanceMBean#dump()
	 */
	@Override
	public void dump() {
		System.out.println(entryMap.toString());

	}

	@Override
	public List<Entry> getAll() {
		return new ArrayList<Entry>(entryMap.values());
	}

	@Override
	public float getAverage() {
		if (count != 0) {
			return sum / count;
		} else {
			return 0;
		}
	}

	@Override
	public float getAverage(String service) {

		String keyPart = service + ":";

		long countInternal = 0;
		float sumInternal = 0;
		Set<String> keys = entryMap.keySet();
		for (String key : keys) {
			if (key.startsWith(keyPart)) {
				Entry entry = entryMap.get(key);
				sumInternal += entry.getSum();
				countInternal += entry.getCount();
			}
		}

		if (countInternal != 0) {
			return sumInternal / countInternal;
		} else {
			return (float) 0.0;
		}
	}

	@Override
	public float getAverage(String service, String method) {
		String key = service + ":" + method;
		Entry entry = entryMap.get(key);
		if (null != entry) {
			return entry.getAverage();
		} else {

			return (float) 0.0;
		}

	}

}
