package FlightClient;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.FlightOfferReply;
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
    @FXML
    private ListView<FlightOfferReply> flightOfferReplyListView;

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

    /**
     * Function to add a FlightOfferReply to the flightOfferReplyListView
     *
     * @param flightOfferReply the FlightOfferReply object that needs to be added to the flightOfferReplyListView
     */
    @Override
    public void addFlightOfferReply(FlightOfferReply flightOfferReply) {
        flightOfferReplyListView.getItems().add(flightOfferReply);
    }
}
