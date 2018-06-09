package JMS;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;

/**
 * Created by Martijn van der Pol on 07-06-18
 **/
class JMS {

    // Specify the connection parameters.
    private final static String WIRE_LEVEL_ENDPOINT = "ssl://b-51062827-8897-4d2a-94a2-827f12f22625-1.mq.eu-west-1.amazonaws.com:61617";
    private final static String ACTIVE_MQ_USERNAME = "MartijnPol";
    private final static String ACTIVE_MQ_PASSWORD = "FlightBroker123";

    /**
     * Create a pooled connection factory.
     *
     * @param connectionFactory is the connectionFactory
     * @return the created PooledConnectionFactory
     */
    static PooledConnectionFactory createPooledConnectionFactory(ActiveMQConnectionFactory connectionFactory) {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(connectionFactory);
        return pooledConnectionFactory;
    }

    /**
     * Create a ConnectionFactory.
     *
     * @return a valid ConnectionFactory
     */
    static ActiveMQConnectionFactory createActiveMQConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(WIRE_LEVEL_ENDPOINT);

        connectionFactory.setTrustAllPackages(true);
        connectionFactory.setUserName(ACTIVE_MQ_USERNAME);
        connectionFactory.setPassword(ACTIVE_MQ_PASSWORD);

        return connectionFactory;
    }
}
