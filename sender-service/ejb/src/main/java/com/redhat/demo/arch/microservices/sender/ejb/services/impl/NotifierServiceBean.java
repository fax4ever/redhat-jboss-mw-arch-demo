/*
 *
 */
package com.redhat.demo.arch.microservices.sender.ejb.services.impl;

import java.io.IOException;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;

@Stateless
@LocalBean
public class NotifierServiceBean {
    @Inject
    private Logger LOG;

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "java:/queue/DemoQueue")
    private Queue queue;

    public void notifySync(String payload) throws IOException {

        Connection connection = null;
        try {
            Destination destination = queue;
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session
                    .createProducer(destination);
            connection.start();
            TextMessage message = session.createTextMessage();
            message.setText(payload);
            messageProducer.send(message);
        } catch (JMSException e) {
            LOG.error("", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
