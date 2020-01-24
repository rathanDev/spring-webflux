package org.jana.webflux.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BusinessFlightData {

    @JsonProperty("data")
    private List<BusinessFlight> data;

    public List<BusinessFlight> getData() {
        return data;
    }

    public void setData(List<BusinessFlight> data) {
        this.data = data;
    }

}
