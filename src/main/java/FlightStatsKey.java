import java.io.Serializable;

public class FlightStatsKey implements Serializable {
    private float maxDelay;
    private int lateFlights;
    private int cancelledFlights;
    private int flights;

    public float getMaxDelay() {
        return maxDelay;
    }

    public int getLateFlights() {
        return lateFlights;
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

    public void setLateFlights(int lateFlights) {
        this.lateFlights = lateFlights;
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
