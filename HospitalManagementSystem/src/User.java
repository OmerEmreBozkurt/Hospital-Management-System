import java.sql.Date;

public class User {
    int userId;
    String userType;
    String username;
    String NameSurname;
    Date dob;
    String gender;
    long phoneNumber;
    String address;

    public User(int userId, String userType, String username, String NameSurname, Date dob, String gender, long phoneNumber, String address){
        this.userId=userId;
        this.userType=userType;
        this.username=username;
        this.NameSurname=NameSurname;
        this.dob=dob;
        this.gender=gender;
        this.phoneNumber=phoneNumber;
        this.address=address;
    }

    @Override
    public String toString() {
        return
                "userId=" + userId +
                ", userType= '" + userType + '\n' +
                ", username= '" + username + '\n' +
                ", NameSurname= '" + NameSurname + '\n' +
                ", dob= " + dob + '\n' +
                ", gender= '" + gender + '\n' +
                ", phoneNumber= " + phoneNumber + '\n' +
                ", address= '" + address ;
    }

    public int getUserId() {
        return userId;
    }
}
