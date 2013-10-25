/**
 * 
 */
package com.prodyna.conference.core.interceptor;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.prodyna.conference.core.event.PerformanceEvent;

@PerfomanceMeasuring
@Interceptor
public class PerformanceMeasuringInterceptor {

	@Inject
	private Logger log;

	@Inject
	private Event<PerformanceEvent> perfEvent;

	/**
	 * 
	 */
	public PerformanceMeasuringInterceptor() {

	}

	/**
	 * @param ic
	 * @return
	 * @throws Exception
	 */
	@AroundInvoke
	public Object aroundInvoke(InvocationContext ic) throws Exception {

		String key = ic.getTarget().getClass() + "." + ic.getMethod().getName();

		StringBuffer params = new StringBuffer();

		Object[] p = ic.getParameters();
		if (null != p && p.length > 0) {
			boolean first = true;
			for (int i = 0; i < p.length; i++) {
				if( !first){
					params.append(";");
					first = false;
				}
				params.append("p").append(i).append(":").append(p[i]);
			}
		}else{
			params.append("NO PARAMS");
		}

		long start = System.currentTimeMillis();
		Object result = null;
		boolean success = false;
		try {
			result = ic.proceed();
			success = true;
		} finally {
			long end = System.currentTimeMillis();
			long duration = end - start;
			log.log(Level.INFO, "PERFORMANCEMEASURING - " + (success ? "SUCCESS -" : "FAILED -") + key
					+ " was called with " + params.toString() + " (Duration: " + duration + " ms)");
			firePerformanceEvent(ic.getTarget().getClass().getName(), ic
					.getMethod().getName(), duration);

		}
		return result;
	}

	private void firePerformanceEvent(String service, String method, long time) {

		PerformanceEvent event = new PerformanceEvent();

		event.setKey(service + ":" + method + ":" + time);
		event.setService(service);
		event.setMethod(method);
		event.setTime(time);

		perfEvent.fire(event);

	}

}
