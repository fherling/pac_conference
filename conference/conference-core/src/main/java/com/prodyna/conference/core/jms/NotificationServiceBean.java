/**
 * 
 */
package com.prodyna.conference.core.jms;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.JMSException;
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
	public void notify(String msg) {

		Connection con = null;
		try {
			log.info("Send message via NotificationService");
			con = connFactory.createConnection();

			con.start();

			Session s = con.createSession(true, Session.AUTO_ACKNOWLEDGE);

			MessageProducer p = s.createProducer(testQueue);
			TextMessage tm = s.createTextMessage();
			tm.setText( msg);

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
