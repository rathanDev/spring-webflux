package org.jana.webflux.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheapFlight extends Flight {

    @JsonProperty("arrival")
    @Override
    public void setArrivalTime(int arrivalTime) {
        super.setArrivalTime(arrivalTime);
    }

    @JsonProperty("departure")
    @Override
    public void setDepartureTime(int departureTime) {
        super.setDepartureTime(departureTime);
    }

}
