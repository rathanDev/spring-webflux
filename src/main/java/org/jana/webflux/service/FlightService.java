package org.jana.webflux.service;

import org.jana.webflux.model.BusinessFlightData;
import org.jana.webflux.model.CheapFlightData;
import org.jana.webflux.model.FlightType;
import org.jana.webflux.model.TokiFlight;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.logging.Logger;

@Service
public class FlightService {
    private static final Logger log = Logger.getLogger(FlightService.class.getName());

    @Value("${api.cheapFlights}")
    String cheapFlightApi;

    @Value("${api.businessFlights}")
    String businessFlightApi;

    private final WebClient webClient;

    public FlightService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<TokiFlight> getAllCheapFlights() {
        log.info("Get all cheap flights. Sort them.");

        return webClient
                .get()
                .uri(cheapFlightApi)
                .retrieve()
                .bodyToMono(CheapFlightData.class)
                .map(CheapFlightData::getData)
                .flatMapMany(Flux::fromIterable)
                .map(e -> new TokiFlight(e.getRoute(), FlightType.CHEAP, e.getDepartureTime(), e.getArrivalTime()))
                .sort();
    }


    public Flux<TokiFlight> getAllBusinessFlights() {
        log.info("Get all business flights. Sort them.");

        return webClient
                .get()
                .uri(businessFlightApi)
                .retrieve()
                .bodyToMono(BusinessFlightData.class)
                .map(BusinessFlightData::getData)
                .flatMapMany(Flux::fromIterable)
                .map(e -> new TokiFlight(e.getDeparture() + "-" + e.getArrival(), FlightType.BUSINESS, e.getArrivalTime(), e.getDepartureTime()))
                .sort();
    }

}
