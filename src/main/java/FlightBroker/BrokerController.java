package FlightBroker;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Martijn van der Pol on 30-05-18
 **/
public class BrokerController implements IBrokerController {

    private int interval = 45;
    private Timer timer;
    private BrokerGateway brokerGateway;

    @FXML
    private ListView<String> flightOfferListView;
    @FXML
    private Label timerLabel;

    /**
     * Constructor to initialize some connections with AmazonMQ
     */
    public BrokerController() {
        this.brokerGateway = new BrokerGateway(this);
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (interval > 0) {
                    Platform.runLater(() -> setTimerText(interval));
                    interval--;
                } else {
                    timer.cancel();
                    brokerGateway.sendCheapestReplyToClient();
                }
            }
        }, 1000, 1000);
    }

    /**
     * Function to add a FlightOfferRequest to the flightOfferListView
     *
     * @param object is the object that needs to be added to the ListView
     */
    public void addToListView(Serializable object) {
        this.flightOfferListView.getItems().add(object.toString());
    }
    
    /**
     * Set TimerText with the given interval
     *
     * @param interval the new interval
     */
    private void setTimerText(int interval) {
        timerLabel.setText(String.valueOf(interval));
    }

}
