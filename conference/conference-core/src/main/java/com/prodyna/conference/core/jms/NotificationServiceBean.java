/**
 * 
 */
package com.prodyna.conference.core.jms;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.google.gson.Gson;

/**
 * The NotificationServiceBean will 
 * send a JSON based message to a QUEUE.
 * @author fherling
 *
 */
@Stateless
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
	public void notify(Object msg) {

		Connection con = null;
		try {
			log.info("Send message via NotificationService");
			con = connFactory.createConnection();

			con.start();

			Session s = con.createSession(true, Session.AUTO_ACKNOWLEDGE);

			MessageProducer p = s.createProducer(testQueue);
			TextMessage tm = s.createTextMessage();
			
			Gson gson = new Gson();
			String queueMsg = gson.toJson(msg);
			
			log.info("Send : " + queueMsg);
			
			tm.setText( queueMsg);
			tm.setStringProperty("jsonClass", msg.getClass().getName());

			p.send(tm);

			p.close();
			s.commit();
			s.close();
			con.stop();
			con.close();

		} catch (Exception e) {
			log.log(Level.SEVERE, "NotificationService call failed",  e);
		}finally{
			if( null != con){
				try {
					con.close();
				} catch (JMSException e) {
					log.log(Level.SEVERE, "Cannot close connection", e);
				}
			}
		}

	}

}
