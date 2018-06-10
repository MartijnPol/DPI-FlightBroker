package models;

import java.io.Serializable;

/**
 * Created by Martijn van der Pol on 09-06-18
 **/
public class FlightOfferReply implements Serializable {

    private String airlineName;
    private Double price;

    public FlightOfferReply(String airlineName, Double price) {
        this.airlineName = airlineName;
        this.price = price;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String toString() {
        return "Price offer from " + this.airlineName + ": " + this.price;
    }
}
