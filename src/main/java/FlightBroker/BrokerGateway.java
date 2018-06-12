package FlightBroker;

import JMS.ConsumerGateway;
import JMS.ProducerGateway;
import JMS.QueueType;
import javafx.application.Platform;
import models.FlightOfferReply;
import models.FlightOfferRequest;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

/**
 * Created by Martijn van der Pol on 12-06-18
 **/
class BrokerGateway {

    private IBrokerController brokerController;
    private ConsumerGateway consumerGateway;
    private ProducerGateway producerGateway;

    BrokerGateway(IBrokerController brokerController) {

        this.brokerController = brokerController;
        this.consumerGateway = new ConsumerGateway();
        this.producerGateway = new ProducerGateway();

        // Set listener to handle the received message from the CLIENT_BROKER_REQUEST Queue
        this.consumerGateway.queueMessageListener(receivedFlightOffer -> {
            if (receivedFlightOffer instanceof ObjectMessage) {
                try {
                    FlightOfferRequest flightOfferRequest = (FlightOfferRequest) ((ObjectMessage) receivedFlightOffer).getObject();
                    Platform.runLater(() -> this.brokerController.addToListView(flightOfferRequest));
                    sendFlightOfferToAirlines(flightOfferRequest);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }, QueueType.CLIENT_BROKER_REQUEST.toString());

        // Set listener to handle the received message from the AIRLINE_BROKER_REPLY Queue
        this.consumerGateway.queueMessageListener(receivedFlightOfferReply -> {
            if (receivedFlightOfferReply instanceof ObjectMessage) {
                try {
                    FlightOfferReply flightOfferReply = (FlightOfferReply) ((ObjectMessage) receivedFlightOfferReply).getObject();
                    Platform.runLater(() -> this.brokerController.addToListView(flightOfferReply));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }, QueueType.AIRLINE_BROKER_REPLY.toString());

    }


    /**
     * Function to send a FlightOfferRequest to the subscribers
     *
     * @param flightOfferRequest the FlightOfferRequest object that needs to be send
     * @throws JMSException is the JMS exception
     */
    private void sendFlightOfferToAirlines(FlightOfferRequest flightOfferRequest) throws JMSException {
        this.producerGateway.sendObjectViaTopic(flightOfferRequest, "FlightOfferTopic");
    }

    /**
     * Function to send a FlightOfferReply to the client
     *
     * @param flightOfferReply is the FlightOfferReply needs to be send
     * @throws JMSException exception
     */
    private void sendFlightOfferReplyToClient(FlightOfferReply flightOfferReply) throws JMSException {
        this.producerGateway.sendObjectViaQueue(flightOfferReply, QueueType.BROKER_CLIENT_REPLY.toString());
    }

}
