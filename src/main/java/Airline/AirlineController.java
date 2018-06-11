package Airline;

import JMS.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.FlightOffer;
import models.FlightOfferReply;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

/**
 * Created by Martijn van der Pol on 30-05-18
 **/
public class AirlineController {

    private ProducerGateway producerGateway;
    private ConsumerGateway consumerGateway;

    @FXML
    private ListView<FlightOffer> flightOfferListView;

    @FXML
    private TextField priceTextField;

    public AirlineController() {

        this.consumerGateway = new ConsumerGateway();
        this.producerGateway = new ProducerGateway();

        // Set listener to handle the received message from the FlightTopic
        consumerGateway.topicMessageListener(receivedFlightOffer -> {
            if (receivedFlightOffer instanceof ObjectMessage) {
                try {
                    FlightOffer flightOffer = (FlightOffer) ((ObjectMessage) receivedFlightOffer).getObject();
                    addFlightOffer(flightOffer);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }, "FlightOfferTopic");

    }

    /**
     * Function to add a FlightOffer to the flightOfferListView
     *
     * @param flightOffer the FlightOffer object that needs to be added to the ListView
     */
    private void addFlightOffer(FlightOffer flightOffer) {
        Platform.runLater(() -> this.flightOfferListView.getItems().add(flightOffer));
    }

    @FXML
    private void sendFlightOfferReply() {
        FlightOffer selectedFlightOffer = flightOfferListView.getSelectionModel().getSelectedItem();
        if (selectedFlightOffer != null) {
            try {
                FlightOfferReply flightOfferReply = new FlightOfferReply("Transavia", new Double(priceTextField.getText()), selectedFlightOffer);
                producerGateway.sendObjectViaQueue(flightOfferReply, QueueType.AIRLINE_BROKER_REPLY.toString());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}
