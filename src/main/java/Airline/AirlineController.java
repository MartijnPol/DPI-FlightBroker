package Airline;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.FlightOfferReply;
import models.FlightOfferRequest;

/**
 * Created by Martijn van der Pol on 30-05-18
 **/
public class AirlineController implements IAirlineController {

    private AirlineGateway airlineGateway;

    @FXML
    private ListView<FlightOfferRequest> flightOfferListView;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField airlineTextField;

    public AirlineController() {
        this.airlineGateway = new AirlineGateway(this);
    }

    /**
     * Function to add a FlightOfferRequest to the flightOfferListView
     *
     * @param flightOfferRequest the FlightOfferRequest object that needs to be added to the ListView
     */
    public void addFlightOffer(FlightOfferRequest flightOfferRequest) {
        this.flightOfferListView.getItems().add(flightOfferRequest);
    }

    @FXML
    private void sendFlightOfferReply() {
        FlightOfferRequest selectedFlightOfferRequest = flightOfferListView.getSelectionModel().getSelectedItem();
        if (selectedFlightOfferRequest != null) {
            FlightOfferReply flightOfferReply = new FlightOfferReply(airlineTextField.getText(), new Double(priceTextField.getText()), selectedFlightOfferRequest);
            this.airlineGateway.sendFlightOfferReplyToBroker(flightOfferReply);
        }
    }

}
