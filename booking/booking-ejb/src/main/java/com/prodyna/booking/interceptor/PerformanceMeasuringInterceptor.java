/**
 * 
 */
package com.prodyna.booking.interceptor;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import com.prodyna.booking.event.PerformanceEvent;
import com.prodyna.booking.jms.HelloService;

@PerfomanceMeasuring
@Interceptor
/**
 * @author fherling
 *
 */
public class PerformanceMeasuringInterceptor {

	@Inject
	private Logger log;
	
	@Inject
	private MBeanServer mbs;

	@Inject
	private Event<PerformanceEvent> perfEvent;
	
	@Inject
	private HelloService helloService;
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

		String key = ic.getTarget().getClass() + "." +   ic.getMethod().getName();
		helloService.hello(key);
		
		long start = System.currentTimeMillis();
		Object result = null;
		try {
			result = ic.proceed();
		} finally {
			long end = System.currentTimeMillis();
			log.log(Level.INFO, "PERFORMANCEMEASURING - Method call duration: " + (end - start) + " ms");
			firePerformanceEvent(ic.getTarget().getClass().getName(), ic.getMethod().getName(), (end -  start));	
			
			
		}
		return result;
	}
	
	private void firePerformanceEvent(String service, String method, long time){
		
		PerformanceEvent event = new PerformanceEvent();
		
		event.setKey(service + ":" + method + ":" + time);
		event.setService(service);
		event.setMethod(method);
		event.setTime(time);
		
		perfEvent.fire(event);
		
	}

}
