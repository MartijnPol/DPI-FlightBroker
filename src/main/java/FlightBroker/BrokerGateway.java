package FlightBroker;

import JMS.ConsumerGateway;
import JMS.ProducerGateway;
import JMS.QueueType;
import javafx.application.Platform;
import models.FlightOfferReply;
import models.FlightOfferRequest;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import java.util.*;

/**
 * Created by Martijn van der Pol on 12-06-18
 **/
class BrokerGateway {

    private HashMap<String, List<FlightOfferReply>> flightOfferReplyList;
    private IBrokerController brokerController;
    private ConsumerGateway consumerGateway;
    private ProducerGateway producerGateway;

    BrokerGateway(IBrokerController brokerController) {

        this.flightOfferReplyList = new HashMap<>();
        this.brokerController = brokerController;
        this.consumerGateway = new ConsumerGateway();
        this.producerGateway = new ProducerGateway();

        // Set listener to handle the received message from the CLIENT_BROKER_REQUEST Queue
        this.consumerGateway.queueMessageListener(receivedFlightOffer -> {
            if (receivedFlightOffer instanceof ObjectMessage) {
                try {
                    FlightOfferRequest flightOfferRequest = (FlightOfferRequest) ((ObjectMessage) receivedFlightOffer).getObject();
                    Platform.runLater(() -> this.brokerController.addToListView(flightOfferRequest));
                    this.flightOfferReplyList.put(flightOfferRequest.getId(), new ArrayList<>());
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
                    addToFlightOfferReplyList(flightOfferReply);
                    Platform.runLater(() -> this.brokerController.addToListView(flightOfferReply));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }, QueueType.AIRLINE_BROKER_REPLY.toString());

    }

    /**
     * Function to add a FlightOfferReply to the list
     *
     * @param reply is the FlightOfferReply
     */
    private void addToFlightOfferReplyList(FlightOfferReply reply) {
        List<FlightOfferReply> flightOfferReplies = this.flightOfferReplyList.get(reply.getFlightOfferRequest().getId());
        flightOfferReplies.add(reply);
        this.flightOfferReplyList.put(reply.getFlightOfferRequest().getId(), flightOfferReplies);
    }

    /**
     * Function to send a FlightOfferRequest to the subscribers
     *
     * @param flightOfferRequest the FlightOfferRequest object that needs to be send
     * @throws JMSException is the JMS exception
     */
    private void sendFlightOfferToAirlines(FlightOfferRequest flightOfferRequest) {
        try {
            this.producerGateway.sendObjectViaTopic(flightOfferRequest, "FlightOfferTopic");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send the cheapest FlightOfferReply to the client
     */
    void sendCheapestReplyToClient() {
        for (Map.Entry<String, List<FlightOfferReply>> entry : this.flightOfferReplyList.entrySet()) {

            String key = entry.getKey();
            List<FlightOfferReply> flightOfferReplies = entry.getValue();
            flightOfferReplies.sort(Comparator.comparing(FlightOfferReply::getPrice));
            FlightOfferReply cheapestFlightOffer = flightOfferReplies.get(0);

            sendFlightOfferReplyToClient(cheapestFlightOffer);
            this.flightOfferReplyList.remove(key);
        }
    }

    /**
     * Function to send a FlightOfferReply to the client
     *
     * @param flightOfferReply is the FlightOfferReply needs to be send
     * @throws JMSException exception
     */
    private void sendFlightOfferReplyToClient(FlightOfferReply flightOfferReply) {
        try {
            this.producerGateway.sendObjectViaQueue(flightOfferReply, QueueType.BROKER_CLIENT_REPLY.toString());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
