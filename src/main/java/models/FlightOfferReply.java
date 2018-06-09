package models;

import java.io.Serializable;

/**
 * Created by Martijn van der Pol on 09-06-18
 **/
public class FlightOfferReply implements Serializable {

    private String airlineName;
    private String message;

    public FlightOfferReply(String airlineName, String message) {
        this.airlineName = airlineName;
        this.message = message;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "Price offer from " + this.airlineName + ": " + this.message;
    }
}
