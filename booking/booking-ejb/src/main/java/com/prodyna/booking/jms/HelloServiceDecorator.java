/**
 * 
 */
package com.prodyna.booking.jms;

import javax.annotation.Resource;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

@Decorator
/**
 * @author fherling
 *
 */
public abstract class HelloServiceDecorator implements HelloService {

	@Resource(lookup = "queue/test")
	private Queue testQueue;
	
	@Resource(lookup = "ConnectionFactory")
	private QueueConnectionFactory connFactory;

	/**
	 * 
	 */
	@Inject
	@Delegate
	@Any
	private HelloService helloService;

	/**
	 * 
	 */
	public HelloServiceDecorator() {
	}

	public void hello(String name) {
		try {
			Connection con = connFactory.createConnection();

			con.start();

			Session s = con.createSession(true, Session.AUTO_ACKNOWLEDGE);

			MessageProducer p = s.createProducer(testQueue);
			TextMessage tm = s.createTextMessage();
			tm.setText("Helloservice called with : " + name);

			p.send(tm);

			p.close();
			s.commit();
			s.close();
			con.stop();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
