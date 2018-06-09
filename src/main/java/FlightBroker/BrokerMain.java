package FlightBroker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Martijn van der Pol on 30-05-18
 **/
public class BrokerMain extends Application {

    /**
     * Function to start the FlightBroker application
     *
     * @param primaryStage is the stage of the application
     * @throws Exception
     */
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/BrokerFrame.fxml"));
        primaryStage.setTitle("FlightBroker");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
