package JMS;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;

import javax.jms.*;
import java.io.Serializable;

/**
 * Created by Martijn van der Pol on 07-06-18
 **/
public class ProducerGateway {

    /**
     * Constructor
     */
    public ProducerGateway() {
    }

    /**
     * Send a message over Queue
     *
     * @param object is the object that needs to be send
     * @throws JMSException is the possible JMS exception
     */
    public void sendObjectViaQueue(Serializable object, String channelName) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = JMS.createActiveMQConnectionFactory();
        PooledConnectionFactory pooledConnectionFactory = JMS.createPooledConnectionFactory(connectionFactory);
        Connection producerConnection = pooledConnectionFactory.createConnection();
        Session producerSession = producerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create a queue named "FlightQueue" and a topic names "FlightTopic"
        Destination flightQueue = producerSession.createQueue("FlightBrokerQueue." + channelName);

        // Create a producer from the session to the queue and the topic
        MessageProducer messageQueueProducer = producerSession.createProducer(flightQueue);
        messageQueueProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        producerConnection.start();

        ObjectMessage flightOfferMessage = producerSession.createObjectMessage(object);
        messageQueueProducer.send(flightOfferMessage);
        System.out.println("FlightOfferRequest sent via FlightBrokerQueue." + channelName);

        // Close connections
        messageQueueProducer.close();
        producerConnection.close();
        producerSession.close();
    }

    /**
     * Send a message over Topic
     *
     * @param object is the object that needs to be send
     * @throws JMSException
     */
    public void sendObjectViaTopic(Serializable object, String topicName) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = JMS.createActiveMQConnectionFactory();
        PooledConnectionFactory pooledConnectionFactory = JMS.createPooledConnectionFactory(connectionFactory);
        Connection producerConnection = pooledConnectionFactory.createConnection();
        Session producerSession = producerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        Topic flightBrokerTopic = producerSession.createTopic(topicName);

        // Create a producer from the session to the topic
        MessageProducer messageTopicProducer = producerSession.createProducer(flightBrokerTopic);
        producerConnection.start();

        ObjectMessage flightOfferMessage = producerSession.createObjectMessage(object);
        messageTopicProducer.send(flightOfferMessage);
        System.out.println("FlightOfferRequest sent via Topic");

        // Close connections
        messageTopicProducer.close();
        producerConnection.close();
        producerSession.close();
    }

}
