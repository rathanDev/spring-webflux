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

    public Flux<TokiFlight> getAllFlights() {
        log.info("Aggregate both cheap & business flights. Sort them");

        Flux<TokiFlight> cheapFlights = webClient
                .get()
                .uri(cheapFlightApi)
                .retrieve()
                .bodyToMono(CheapFlightData.class)
                .map(CheapFlightData::getData)
                .flatMapMany(Flux::fromIterable)
                .map(e -> new TokiFlight(e.getRoute(), FlightType.CHEAP, e.getDepartureTime(), e.getArrivalTime()));

        Flux<TokiFlight> businessFlights = webClient
                .get()
                .uri(businessFlightApi)
                .retrieve()
                .bodyToMono(BusinessFlightData.class)
                .map(BusinessFlightData::getData)
                .flatMapMany(Flux::fromIterable)
                .map(e -> new TokiFlight(e.getDeparture() + "-" + e.getArrival(), FlightType.BUSINESS, e.getArrivalTime(), e.getDepartureTime()));

        return Flux
                .concat(cheapFlights, businessFlights)
                .sort();
    }

    public Flux<TokiFlight> getFlightsByPage(int page, int size) {
        log.info("Get flights by page. page:" + page + " size:" + size);

        return getAllFlights()
                .skip(page * size)
                .take(size);
    }

    public Flux<TokiFlight> getFlightsByFilter(String flightTypeString,
                                               String route,
                                               long arrivalTimeFrom,
                                               long arrivalTimeTo) {
        log.info("Get flights by filter type:" + flightTypeString + " route:" + route + " arrivalTimeFrom:" + arrivalTimeFrom + " arrivalTimeTo:" + arrivalTimeTo);

        Flux<TokiFlight> flights;

        if (flightTypeString != null && !flightTypeString.trim().isEmpty()) {
            FlightType flightType;
            try {
                flightType = FlightType.valueOf(flightTypeString.toUpperCase());

                if (flightType == FlightType.CHEAP) {
                    flights = getAllCheapFlights();
                } else if (flightType == FlightType.BUSINESS) {
                    flights = getAllBusinessFlights();
                } else {
                    flights = getAllFlights();
                }
            } catch (Exception e) {
                log.warning("Invalid flight type: " + flightTypeString);
                flights = getAllFlights();
            }
        } else {
            flights = getAllFlights();
        }

        if (route != null && !route.trim().isEmpty()) {
            flights = flights.filter(e -> e.getRoute().contains(route));
        }

        if (arrivalTimeFrom != 0) {
            flights = flights.filter(e -> e.getArrivalTime() >= arrivalTimeFrom);
        }

        if (arrivalTimeTo != 0) {
            flights = flights.filter(e -> e.getArrivalTime() <= arrivalTimeTo);
        }

        return flights;
    }

    public Flux<TokiFlight> getFlightsByFilterPage(String flightTypeString,
                                                   String route,
                                                   long arrivalTimeFrom,
                                                   long arrivalTimeTo,
                                                   int page,
                                                   int size) {
        log.info("Get flights by filter-page type:" + flightTypeString + " route:" + route + " arrivalTimeFrom:" + arrivalTimeFrom + " arrivalTimeTo:" + arrivalTimeTo + " page:" + page + " size:" + size);

        Flux<TokiFlight> flights = getFlightsByFilter(flightTypeString, route, arrivalTimeFrom, arrivalTimeTo);

        if (page != 0 && size != 0) {
            flights = flights
                    .skip(page * size)
                    .take(size);
        }

        return flights;
    }

}
