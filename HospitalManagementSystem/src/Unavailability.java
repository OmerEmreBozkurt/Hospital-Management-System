import java.sql.Date;

public class Unavailability {
    int doctorId;
    String doctorName;
    Date unavailabilityStart;
    Date unavailabilityEnd;

    public Unavailability(int doctorId, Date unavailabilityStart, Date unavailabilityEnd, String doctorName){
        this.doctorId=doctorId;
        this.unavailabilityStart=unavailabilityStart;
        this.unavailabilityEnd=unavailabilityEnd;
        this.doctorName=doctorName;
    }

    public String toString() {
        return doctorName + " is unavailable between " + unavailabilityStart + " and " + unavailabilityEnd;
    }
}
