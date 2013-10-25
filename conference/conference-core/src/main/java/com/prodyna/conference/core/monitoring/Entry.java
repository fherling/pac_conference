/**
 * 
 */
package com.prodyna.conference.core.monitoring;

/**
 * @author fherling
 *
 */
public class Entry {

	@Override
	public String toString() {
		return "Entry [service=" + service + ", method=" + method
				+ ", minTime=" + minTime + ", maxTime=" + maxTime + ", count="
				+ count + ", sum=" + sum + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (count ^ (count >>> 32));
		result = prime * result + (int) (maxTime ^ (maxTime >>> 32));
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + (int) (minTime ^ (minTime >>> 32));
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		result = prime * result + (int) (sum ^ (sum >>> 32));
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
		Entry other = (Entry) obj;
		if (count != other.count)
			return false;
		if (maxTime != other.maxTime)
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (minTime != other.minTime)
			return false;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		if (sum != other.sum)
			return false;
		return true;
	}


	private String service;
	private String method; 
	private long minTime;
	private long maxTime;
	private long count;
	private long sum;
	
	public Entry(String service, String method){
		this.service = service;
		this.method = method;
	}
	
	public String getService() {
		return service;
	}

	public String getMethod() {
		return method;
	}

	public long getMinTime() {
		return minTime;
	}

	public long getMaxTime() {
		return maxTime;
	}

	public long getCount() {
		return count;
	}

	public long getSum() {
		return sum;
	}

	
	
	public void report(long time){
		if( time < minTime){
			minTime = time;
		}
		if( time > maxTime){
			maxTime = time;
		}
		count++;
		sum += time;
	}
	
	
	public float getAverage(){
		if( count != 0 ){
			return (float)sum / (float)count;
		}else{
			return 0;
		}
	}
}
