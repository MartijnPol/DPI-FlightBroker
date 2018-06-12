package FlightClient;

import JMS.ConsumerGateway;
import JMS.ProducerGateway;
import JMS.QueueType;
import models.FlightOfferRequest;

import javax.jms.JMSException;

/**
 * Created by Martijn van der Pol on 12-06-18
 **/
class ClientGateway {

    private IClientController clientController;
    private ProducerGateway producerGateway;
    private ConsumerGateway consumerGateway;

    ClientGateway(IClientController clientController) {
        this.clientController = clientController;
        this.producerGateway = new ProducerGateway();
        this.consumerGateway = new ConsumerGateway();
    }

    void sendFlightOfferRequest(FlightOfferRequest flightOfferRequest) {
        try {
            this.producerGateway.sendObjectViaQueue(flightOfferRequest, QueueType.CLIENT_BROKER_REQUEST.toString());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
