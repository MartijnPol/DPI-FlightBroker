package FlightBroker;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.Serializable;

/**
 * Created by Martijn van der Pol on 30-05-18
 **/
public class BrokerController implements IBrokerController {

    @FXML
    private ListView<String> flightOfferListView;

    /**
     * Constructor to initialize some connections with AmazonMQ
     */
    public BrokerController() {
        BrokerGateway brokerGateway = new BrokerGateway(this);
    }

    /**
     * Function to add a FlightOfferRequest to the flightOfferListView
     *
     * @param object is the object that needs to be added to the ListView
     */
    public void addToListView(Serializable object) {
        this.flightOfferListView.getItems().add(object.toString());
    }

}
