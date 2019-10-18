import java.io.Serializable;

public class FlightStatsKey implements Serializable {
    private float maxDelay;
    private int lateFlights;
    private int cancelledFlights;
    private int flights;

    @Override
    public String toString() {
        return super.toString();
    }
}
