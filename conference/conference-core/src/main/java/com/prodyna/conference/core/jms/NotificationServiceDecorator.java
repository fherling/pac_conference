/**
 * 
 */
package com.prodyna.conference.core.jms;

import java.util.logging.Level;
import java.util.logging.Logger;

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

@Decorator
/**
 * @author fherling
 *
 */
public abstract class NotificationServiceDecorator implements NotificationService {

	@Resource(lookup = "queue/test")
	private Queue testQueue;
	
	@Resource(lookup = "ConnectionFactory")
	private QueueConnectionFactory connFactory;
	
	@Inject
	private Logger log;

	/**
	 * 
	 */
	@Inject
	@Delegate
	@Any
	private NotificationService notificationService;

	/**
	 * 
	 */
	public NotificationServiceDecorator() {
	}

	public void notify(String name) {
		try {
			Connection con = connFactory.createConnection();

			con.start();

			Session s = con.createSession(true, Session.AUTO_ACKNOWLEDGE);

			MessageProducer p = s.createProducer(testQueue);
			TextMessage tm = s.createTextMessage();
			tm.setText("Notification was send with : " + name);

			p.send(tm);

			p.close();
			s.commit();
			s.close();
			con.stop();
			con.close();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}

}
