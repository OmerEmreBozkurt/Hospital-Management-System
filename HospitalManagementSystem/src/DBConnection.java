import java . sql . Connection ;
import java . sql . DriverManager ;
import java . sql . SQLException ;

public class DBConnection {
    final static String url = "jdbc:mysql://localhost:3306/cs202_project";
    final static String user = "root";
    final static String password = "111111";

    public static Connection getConnection() {
        Connection myConn = null;

        try {
            myConn = DriverManager.getConnection(url, user,password);
            if (myConn != null) {
                System.out.println(" Connected to the database !");
                // Continue to the next steps ...
            }
            else {
                System.out.println(" Failed to make a connection !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myConn;
    }
}