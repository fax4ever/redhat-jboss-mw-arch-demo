/*
 *
 */
package com.redhat.demo.arch.microservices.consumer.ejb.mdb;

import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.redhat.demo.arch.microservices.consumer.ejb.services.impl.CounterUpdaterServiceBean;

@MessageDriven(name = "DemoQueueMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "DemoQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class DemoQueueMDB implements MessageListener {

    private final static Logger LOG = Logger.getLogger(DemoQueueMDB.class.toString());

    @EJB
    private CounterUpdaterServiceBean counterUpdaterService;

    /**
     * @see MessageListener#onMessage(Message)
     */
    @Override
    public void onMessage(Message rcvMessage) {
        TextMessage msg = null;
        try {
            if (rcvMessage instanceof TextMessage) {
                msg = (TextMessage) rcvMessage;
                LOG.info("Received Message from queue: " + msg.getText());
                counterUpdaterService.updateCounter(msg.getText());
            } else {
                LOG.warning("Message of wrong type: " + rcvMessage.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
