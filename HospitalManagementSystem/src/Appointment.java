import java.sql.Date;

public class Appointment {
    int id;
    Date date;
    int patientId;
    String doctorName;
    String roomName;

    public Appointment(int id,Date date, int patientId, String doctorName, String roomName){
        this.id=id;
        this.date= date;
        this.patientId=patientId;
        this.doctorName=doctorName;
        this.roomName=roomName;
    }

    @Override
    public String toString() {
        return "Appointment " + id +
                ", on " + date +
                ", with " + doctorName +
                ", in Room " + roomName;
    }
}
