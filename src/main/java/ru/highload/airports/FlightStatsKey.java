package ru.highload.airports;

import java.io.Serializable;

public class FlightStatsKey implements Serializable {
    private static final float EPS = 1e-9f;
    private float maxDelay;
    private int delayedFlights;
    private int cancelledFlights;
    private int flights;

    public FlightStatsKey(float maxDelay, int delayedFlights, int cancelledFlights, int flights) {
        this.maxDelay = maxDelay;
        this.delayedFlights = delayedFlights;
        this.cancelledFlights = cancelledFlights;
        this.flights = flights;
    }

    public FlightStatsKey(String delay, String cancelled){
        if (delay.equals("")){
            this.maxDelay = 0.f;
            this.delayedFlights = 0;
        } else {
            this.maxDelay = Float.parseFloat(delay);
            this.delayedFlights = (maxDelay > EPS ? 1 : 0);
        }

        this.cancelledFlights = (Float.parseFloat(cancelled) > EPS ? 1 : 0);
        this.flights = 1;
    }

    public float getMaxDelay() {
        return maxDelay;
    }

    public int getDelayedFlights() {
        return delayedFlights;
    }

    public int getCancelledFlights() {
        return cancelledFlights;
    }

    public int getFlights() {
        return flights;
    }

    public void setMaxDelay(float maxDelay) {
        this.maxDelay = maxDelay;
    }

    public void setDelayedFlights(int delayedFlights) {
        this.delayedFlights = delayedFlights;
    }

    public void setCancelledFlights(int cancelledFlights) {
        this.cancelledFlights = cancelledFlights;
    }

    public void setFlights(int flights) {
        this.flights = flights;
    }

    

    @Override
    public String toString() {
        return super.toString();
    }
}
