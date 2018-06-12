package Airline;

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
class AirlineGateway {

    private ProducerGateway producerGateway;
    private ConsumerGateway consumerGateway;
    private IAirlineController airlineController;

    AirlineGateway(IAirlineController airlineController) {

        this.airlineController = airlineController;
        this.producerGateway = new ProducerGateway();
        this.consumerGateway = new ConsumerGateway();

        // Set listener to handle the received message from the FlightTopic
        this.consumerGateway.topicMessageListener(receivedFlightOffer -> {
            if (receivedFlightOffer instanceof ObjectMessage) {
                try {
                    FlightOfferRequest flightOfferRequest = (FlightOfferRequest) ((ObjectMessage) receivedFlightOffer).getObject();
                    Platform.runLater(() -> this.airlineController.addFlightOffer(flightOfferRequest));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }, "FlightOfferTopic");

    }

    void sendFlightOfferReplyToBroker(FlightOfferReply flightOfferReply) {
        try {
            this.producerGateway.sendObjectViaQueue(flightOfferReply, QueueType.AIRLINE_BROKER_REPLY.toString());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
