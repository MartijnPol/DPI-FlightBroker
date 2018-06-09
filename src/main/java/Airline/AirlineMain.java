package Airline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Martijn van der Pol on 30-05-18
 **/
public class AirlineMain extends Application {

    /**
     * Function to start the FlightBroker application
     *
     * @param primaryStage is the stage of the application
     * @throws Exception
     */
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/AirlineFrame.fxml"));
        primaryStage.setTitle("Airline - Transavia");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
