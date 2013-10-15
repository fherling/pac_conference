/**
 * 
 */
package com.prodyna.conference.core.jms;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author fherling
 *
 */
public class NotificationServiceBean implements NotificationService {

	@Inject
	private Logger log;
	
	@Resource(lookup = "queue/test")
	private Queue testQueue;
	
	@Resource(lookup = "ConnectionFactory")
	private QueueConnectionFactory connFactory;
	
	/* (non-Javadoc)
	 * @see com.prodyna.booking.jms.HelloService#hello(java.lang.String)
	 */
	@Override
	public void notify(String name) {

		try {
			log.info("Send message via NotificationService");
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
			log.log(Level.SEVERE, "NotificationService call failed",  e);
		}

	}

}
