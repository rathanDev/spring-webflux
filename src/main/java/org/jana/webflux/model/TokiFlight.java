package org.jana.webflux.model;

public class TokiFlight extends Flight {

    FlightType flightType;

    public TokiFlight() {
    }

    public TokiFlight(String route, FlightType flightType, int departureTime, int arrivalTime) {
        super(route, departureTime, arrivalTime);
        this.flightType = flightType;
    }

    public FlightType getFlightType() {
        return flightType;
    }

    public void setFlightType(FlightType flightType) {
        this.flightType = flightType;
    }

}
