package org.jana.webflux.model;

public abstract class Flight implements Comparable<Flight> {

    private String route;
    private int departureTime;
    private int arrivalTime;

    public Flight() {
    }

    public Flight(String route, int departureTime, int arrivalTime) {
        this.route = route;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public int getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public int compareTo(Flight o) {

        int arrivalTimeDiff = this.getArrivalTime() - o.getArrivalTime();
        if (arrivalTimeDiff != 0) {
            return arrivalTimeDiff;
        }

        int departureTimeDiff = this.getDepartureTime() - o.getDepartureTime();
        if (departureTimeDiff != 0) {
            return departureTimeDiff;
        }

        return this.route.compareTo(o.getRoute());
    }


}
