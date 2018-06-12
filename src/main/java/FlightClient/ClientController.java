package FlightClient;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.FlightOfferRequest;

/**
 * Created by Martijn van der Pol on 31-05-18
 **/
public class ClientController implements IClientController {

    private ClientGateway clientGateway;

    @FXML
    private TextField nameField;
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
        this.clientGateway = new ClientGateway(this);
    }

    /**
     * Function to send a message to the Broker
     */
    @FXML
    private void sendFlightOffer() {
        FlightOfferRequest flightOfferRequest = createFlightOffer();
        this.clientGateway.sendFlightOfferRequest(flightOfferRequest);
    }

    /**
     * Create a FlightOfferRequest based on given values
     *
     * @return a valid FlightOfferRequest
     */
    private FlightOfferRequest createFlightOffer() {
        return new FlightOfferRequest(nameField.getText(), departureAirportField.getText(),
                arrivalAirportField.getText(),
                departureDatePicker.getValue(),
                returnDatePicker.getValue());
    }

}
