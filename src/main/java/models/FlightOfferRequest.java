package models;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Martijn van der Pol on 07-06-18
 **/
public class FlightOfferRequest implements Serializable {

    private String id;
    private String departureAirport;
    private String arrivalAirport;
    private Date departureDate;
    private Date returnDate;

    public FlightOfferRequest() {
    }

    public FlightOfferRequest(String id, String departureAirport, String arrivalAirport, LocalDate departureDate, LocalDate returnDate) {
        this.id = id;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureDate = Date.from(Instant.from(departureDate.atStartOfDay(ZoneId.systemDefault())));
        this.returnDate = Date.from(Instant.from(returnDate.atStartOfDay(ZoneId.systemDefault())));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String toString() {
        return "FlightOfferRequest received from " + this.departureAirport + " to " + this.arrivalAirport;
    }

}
