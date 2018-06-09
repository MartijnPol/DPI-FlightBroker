package FlightClient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Martijn van der Pol on 31-05-18
 **/
public class ClientMain extends Application {

    /**
     * Function to start the FlightClient application
     *
     * @param primaryStage is the stage of the application
     * @throws Exception is a possible exception
     */
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ClientFrame.fxml"));
        primaryStage.setTitle("FlightClient");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
