package org.jana.webflux.controller;

import org.jana.webflux.model.TokiFlight;
import org.jana.webflux.service.FlightService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/flight")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/cheap/all")
    public Flux<TokiFlight> getAllCheapFlights() {
        return flightService.getAllCheapFlights();
    }

    @GetMapping("/business/all")
    public Flux<TokiFlight> getAllBusinessFlights() {
        return flightService.getAllBusinessFlights();
    }

    @GetMapping("/all")
    public Flux<TokiFlight> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/page")
    public Flux<TokiFlight> getFlightsByPage(@RequestParam int page, @RequestParam int size) {
        return flightService.getFlightsByPage(page, size);
    }

    @GetMapping("/filter")
    public Flux<TokiFlight> getFlightsByFilter(@RequestParam(required = false) String flightType,
                                               @RequestParam(required = false) String route,
                                               @RequestParam(required = false) Long arrivalTimeFrom,
                                               @RequestParam(required = false) Long arrivalTimeTo) {
        return flightService.getFlightsByFilter(flightType, route, arrivalTimeFrom, arrivalTimeTo);
    }

    @GetMapping("/filter-page")
    public Flux<TokiFlight> getFlightsByFilterPage(@RequestParam(required = false) String flightType,
                                                   @RequestParam(required = false) String route,
                                                   @RequestParam(required = false) Long arrivalTimeFrom,
                                                   @RequestParam(required = false) Long arrivalTimeTo,
                                                   @RequestParam(required = false) Integer page,
                                                   @RequestParam(required = false) Integer size) {
        return flightService.getFlightsByFilterPage(flightType, route, arrivalTimeFrom, arrivalTimeTo, page, size);
    }

}
