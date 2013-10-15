package com.prodyna.conference.core.jms;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: NotificationServiceListener
 */
//@MessageDriven(
//		activationConfig = { @ActivationConfigProperty(
//				propertyName = "destinationType", propertyValue = "javax.jms.Queue"), @ActivationConfigProperty(
//				propertyName = "destination", propertyValue = "queue/test")
//		}, 
//		mappedName = "queue/test")
public class NotificationServiceListener implements MessageListener {

	@Inject
	private Logger log;
	
    /**
     * Default constructor. 
     */
    public NotificationServiceListener() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {

    	TextMessage msg = (TextMessage)message;
    	
    	try {
			System.out.println("MESSAGE RECEIVED: " + msg.getText());
		} catch (JMSException e) {
			log.log(Level.SEVERE, e.getMessage());
		}
    	
        
    }

}
