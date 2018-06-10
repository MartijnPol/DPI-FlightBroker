package FlightBroker;

import JMS.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import models.FlightOffer;
import models.FlightOfferReply;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import java.io.Serializable;

/**
 * Created by Martijn van der Pol on 30-05-18
 **/
public class BrokerController {

    private ConsumerGateway consumerGateway;
    private ProducerGateway producerGateway;

    @FXML
    private ListView<String> flightOfferListView;

    /**
     * Constructor to initialize some connections with AmazonMQ
     */
    public BrokerController() {

        this.consumerGateway = new ConsumerGateway();
        this.producerGateway = new ProducerGateway();

        // Set listener to handle the received message from the CLIENT_BROKER_REQUEST Queue
        this.consumerGateway.queueMessageListener(receivedFlightOffer -> {
            if (receivedFlightOffer instanceof ObjectMessage) {
                try {
                    FlightOffer flightOffer = (FlightOffer) ((ObjectMessage) receivedFlightOffer).getObject();
                    addToListView(flightOffer);
                    sendFlightOfferToAirlines(flightOffer);
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
                    addToListView(flightOfferReply);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }, QueueType.AIRLINE_BROKER_REPLY.toString());

    }

    /**
     * Function to add a FlightOffer to the flightOfferListView
     *
     * @param object is the object that needs to be added to the ListView
     */
    private void addToListView(Serializable object) {
        Platform.runLater(() -> this.flightOfferListView.getItems().add(object.toString()));
    }

    /**
     * Function to send a FlightOffer to the subscribers
     *
     * @param flightOffer the FlightOffer object that needs to be send
     * @throws JMSException is the JMS exception
     */
    private void sendFlightOfferToAirlines(FlightOffer flightOffer) throws JMSException {
        producerGateway.sendObjectViaTopic(flightOffer, "FlightOfferTopic");
    }

}
