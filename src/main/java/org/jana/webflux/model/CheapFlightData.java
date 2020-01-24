package org.jana.webflux.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CheapFlightData {

    @JsonProperty("data")
    private List<CheapFlight> data;

    public List<CheapFlight> getData() {
        return data;
    }

    public void setData(List<CheapFlight> data) {
        this.data = data;
    }

}
