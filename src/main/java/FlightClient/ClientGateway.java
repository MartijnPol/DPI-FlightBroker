package FlightClient;

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
class ClientGateway {

    private IClientController clientController;
    private ProducerGateway producerGateway;

    ClientGateway(IClientController clientController) {

        this.clientController = clientController;
        this.producerGateway = new ProducerGateway();
        ConsumerGateway consumerGateway = new ConsumerGateway();

        // Set listener to handle the received message from the BROKER_CLIENT_REPLY Queue
        consumerGateway.queueMessageListener(receivedFlightOfferReply -> {
            if (receivedFlightOfferReply instanceof ObjectMessage) {
                try {
                    FlightOfferReply flightOfferReply = (FlightOfferReply) ((ObjectMessage) receivedFlightOfferReply).getObject();
                    Platform.runLater(() -> this.clientController.addFlightOfferReply(flightOfferReply));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }, QueueType.BROKER_CLIENT_REPLY.toString());

    }

    void sendFlightOfferRequest(FlightOfferRequest flightOfferRequest) {
        try {
            this.producerGateway.sendObjectViaQueue(flightOfferRequest, QueueType.CLIENT_BROKER_REQUEST.toString());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
