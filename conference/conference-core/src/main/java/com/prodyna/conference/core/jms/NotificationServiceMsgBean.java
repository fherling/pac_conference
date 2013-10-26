
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

import com.google.gson.Gson;

/**
 * Message-Driven Bean implementation class for: NotificationServiceMsgBean
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue"), @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "queue/test")
		}, 
		mappedName = "queue/test")
public class NotificationServiceMsgBean implements MessageListener {

	@Inject
	private Logger log;
	
    
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {

    	TextMessage msg = (TextMessage)message;
    	
    	
    	try {
    		String jsonClass = msg.getStringProperty("jsonClass");
			log.log(Level.INFO, "NOTIFICATION RECEIVED: " + jsonClass + " : "  + msg.getText());
		} catch (JMSException e) {
			log.log(Level.SEVERE, e.getMessage());
		}
    	
        
    }

}
