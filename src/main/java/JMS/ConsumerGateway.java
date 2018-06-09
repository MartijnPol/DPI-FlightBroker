package JMS;

import javax.jms.*;

/**
 * Created by Martijn van der Pol on 07-06-18
 **/
public class ConsumerGateway {

    private MessageConsumer messageQueueConsumer;
    private MessageConsumer messageTopicConsumer;

    /**
     * Constructor
     */
    public ConsumerGateway() {
    }

    /**
     * Listener to receive and handle messages from the Queue
     *
     * @param messageListener is the MessageListener that can be overridden
     */
    public void queueMessageListener(MessageListener messageListener, String channelName) {
        try {
            Connection consumerConnection = JMS.createActiveMQConnectionFactory().createConnection();
            Session consumerSession = consumerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue flightQueue = consumerSession.createQueue("FlightBrokerQueue." + channelName);
            this.messageQueueConsumer = consumerSession.createConsumer(flightQueue);
            this.messageQueueConsumer.setMessageListener(messageListener);
            consumerConnection.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * Listener to receive and handle messages from the Topic
     *
     * @param messageListener is the MessageListener that can be overridden
     */
    public void topicMessageListener(MessageListener messageListener, String topicName) {
        try {
            Connection consumerConnection = JMS.createActiveMQConnectionFactory().createConnection();
            Session consumerSession = consumerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = consumerSession.createTopic(topicName);
            this.messageTopicConsumer = consumerSession.createConsumer(topic);
            this.messageTopicConsumer.setMessageListener(messageListener);
            consumerConnection.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to close the connection with the ActiveMQ broker
     */
    public void closeConnection() {
        try {
            messageQueueConsumer.close();
            messageTopicConsumer.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
