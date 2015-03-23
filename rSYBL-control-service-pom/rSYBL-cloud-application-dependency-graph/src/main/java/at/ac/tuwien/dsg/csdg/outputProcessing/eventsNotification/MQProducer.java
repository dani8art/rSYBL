/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.csdg.outputProcessing.eventsNotification;

import at.ac.tuwien.dsg.csdg.utils.Configuration;
import at.ac.tuwien.dsg.csdg.utils.DependencyGraphLogger;
import java.net.URI;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;


/**
 *
 * @author Georgiana
 */
public class MQProducer {

    private String QUEUE_NAME = "events";
    private String QUEUE_url = "tcp://localhost:61616";

    private Connection connection;
    private Session session = null;
    private MessageProducer producer;

    public MQProducer() {
        if (Configuration.getQueueName()!=null){
            QUEUE_NAME=Configuration.getQueueName();
        }
        if (Configuration.getQueueUrl()!=null){
            QUEUE_url = Configuration.getQueueUrl();
        }
        initiateQueue();

    }

    public void initiateQueue() {
        // get the initial context
        BrokerService broker = new BrokerService();
 

        try {
           // configure the broker
 
            TransportConnector connector = new TransportConnector();
            connector.setUri(new URI(QUEUE_url));
            broker.addConnector(connector);
            broker.start();
        	 ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
        	 
             // Create a Connection
             connection = connectionFactory.createConnection();
             connection.start();

             // Create a Session
             session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

             // Create the destination (Topic or Queue)
             Destination destination = session.createQueue(QUEUE_NAME);

             // Create a MessageProducer from the Session to the Topic or Queue
             producer = session.createProducer(destination);
             producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        } catch (Exception e) {
            DependencyGraphLogger.logger.error(e.getMessage());
        }

    }

    public void sendMessage(IEvent event) {
        try {
            ObjectMessage message = session.createObjectMessage(event);

            // Tell the producer to send the message
            producer.send(message);

        } catch (Exception e) {
            DependencyGraphLogger.logger.error(e.getCause());
        }
    }

    public void closeQueue() {
        try {
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            DependencyGraphLogger.logger.error(e.getCause());
        }
    }
}