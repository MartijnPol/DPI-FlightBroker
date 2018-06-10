package FlightClient;

import JMS.*;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.FlightOffer;

import javax.jms.JMSException;

/**
 * Created by Martijn van der Pol on 31-05-18
 **/
public class ClientController {

    private ProducerGateway producerGateway;

    @FXML
    private TextField departureAirportField;
    @FXML
    private TextField arrivalAirportField;
    @FXML
    private DatePicker departureDatePicker;
    @FXML
    private DatePicker returnDatePicker;

    /**
     * Constructor to initialize some connections with AmazonMQ
     */
    public ClientController() {
        this.producerGateway = new ProducerGateway();
    }

    /**
     * Function to send a message to the Broker
     */
    @FXML
    private void sendFlightOffer() {
        try {
            FlightOffer flightOffer = createFlightOffer();
            this.producerGateway.sendObjectViaQueue(flightOffer, QueueType.CLIENT_BROKER_REQUEST.toString());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a FlightOffer based on given values
     *
     * @return a valid FlightOffer
     */
    private FlightOffer createFlightOffer() {
        return new FlightOffer(departureAirportField.getText(),
                arrivalAirportField.getText(),
                departureDatePicker.getValue(),
                returnDatePicker.getValue());
    }

}
