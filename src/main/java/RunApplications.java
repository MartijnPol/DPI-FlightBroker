import FlightBroker.BrokerMain;
import FlightClient.ClientMain;

/**
 * Created by Martijn van der Pol on 07-06-18
 **/
public class RunApplications {

    public static void main(String[] args) {
        javafx.application.Application.launch(ClientMain.class);
        javafx.application.Application.launch(BrokerMain.class);
    }

}
