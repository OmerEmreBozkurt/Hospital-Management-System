import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

public class Controller implements ActionListener {
    static final String utf8 = "utf-8";
    private Model model;
    private View view;
    int activeUserId;
    String activeUserNameSurname;
    Connection connection = DBConnection.getConnection();
    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
        view.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == view.OpenSignupButton){
            try {
                view.setupSignupFrame();
                view.loginframe.dispatchEvent(new WindowEvent(view.loginframe ,WindowEvent.WINDOW_CLOSING));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (event.getSource() == view.loginButton){
            try {
                String username = view.usernameField.getText();
                char[] PasswordChars = view.passwordField.getPassword();
                String encryptedUserPassword = encrypt(new String(PasswordChars), generateSecretKey());
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT userId,userType,nameSurname " +
                        "FROM Users " +
                        "WHERE userName=('"+ username + "') AND " +
                        "userPassword= ('"+ encryptedUserPassword + "');");
                rs.next();
                activeUserId = rs.getInt(1);
                String userType = rs.getString(2);
                String nameSurname = rs.getString(3);
                activeUserNameSurname = nameSurname;
                System.out.println(activeUserId);
                System.out.println(userType);
                if (userType.equals("Patient")) {
                    view.setupPatientFrame(activeUserId, nameSurname);
                    for (String element:getExpertises()) {
                        view.expertiseBox1.addItem(element);
                    }
                }
                else if (userType.equals("Doctor")) {view.setupDoctorFrame(activeUserId, nameSurname);}
                else if (userType.equals("Nurse")) {view.setupNurseFrame(activeUserId,nameSurname);}
                else if (userType.equals("Manager")) {view.setupAdminFrame(activeUserId, nameSurname);
                    getUsers();
                    view.adminUsersListModel.removeAllElements();
                    for (User element:getUsers()) {
                        view.adminUsersListModel.addElement(element);
                    }
                }
                else {JOptionPane.showMessageDialog(view.loginframe,"Invalid User.");}

                view.loginframe.dispatchEvent(new WindowEvent(view.loginframe ,WindowEvent.WINDOW_CLOSING));
            } catch (Exception e){
                JOptionPane.showMessageDialog(view.loginframe,"Incorrect username or password.");
            }
        }
        if (event.getSource() == view.signupButton){
            try{
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT MAX(userId) FROM Users");
                resultSet.next();
                int userId = resultSet.getInt(1) + 1 ;
                String username = view.usernameField.getText();
                String nameSurname = view.nameSurnameField.getText();
                char[] passwordChars = view.passwordField.getPassword();
                String password = new String(passwordChars);
                String encryptedPassword = encrypt(password,generateSecretKey());
                Date dateOfBirth = stringToDate(view.dateOfBirthField.getText());
                String gender = (String) view.genderBox.getSelectedItem();
                char g = 0;
                if (gender.equals("Male")){g = 'M';}
                else if (gender.equals("Female")) {g = 'F';}
                else {JOptionPane.showMessageDialog(view.loginframe,"Please select a gender");}
                int phoneNumber = Integer.parseInt(view.phoneNumberField.getText());
                String address = view.addressField.getText();
                statement.executeUpdate("INSERT INTO Users (userId, userType, username, " +
                        "nameSurname, userPassword, dateOfBirth, gender, " +
                        "phoneNumber, address) VALUES ('" + userId + "' , 'Patient' ,  '" + username + "' ,  '" + nameSurname + "' ,  '" + encryptedPassword + "' ,  '" + dateOfBirth + "' ,  '" + g + "' ,  '" + phoneNumber + "' ,  '" + address + "');");
                view.signupFrame.dispatchEvent(new WindowEvent(view.signupFrame ,WindowEvent.WINDOW_CLOSING));
                view.setupFrame();
            } catch (SQLIntegrityConstraintViolationException e) {
                JOptionPane.showMessageDialog(view.loginframe,"This username already exists.");
            } catch (Exception e){
                JOptionPane.showMessageDialog(view.loginframe,"Please provide valid values.");
                e.printStackTrace();
            }
        }
        if (event.getSource() == view.filterAppointmentsButton) {
            try {
                int days = Integer.parseInt(view.daysTextField.getText());
                String expertise = (String) view.expertiseBox1.getSelectedItem();
                ArrayList<Appointment> appointments = new ArrayList<>();
                if (expertise.equals("")) {
                    if (view.futureSelectionButton.isSelected()) {
                        Statement statement = connection.createStatement();
                        ResultSet rs = statement.executeQuery("SELECT A.appId, A.appDate, A.patientId, U.nameSurname, R.roomType\n" +
                                "FROM Appointment A, Doctor D, users U, Room R\n" +
                                "WHERE A.doctorId = D.userId \n" +
                                "and A.patientId = '" + activeUserId + "' \n" +
                                "AND A.doctorId = U.userId\n" +
                                "AND R.roomId = A.roomId\n" +
                                "AND A.appDate BETWEEN CURRENT_TIMESTAMP() AND DATE_ADD(current_timestamp(),interval '-100' day);");
                        while (rs.next()) {
                            appointments.add(new Appointment(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getString(4), rs.getString(5)));
                        }
                    }
                    if (view.pastSelectionButton.isSelected()) {
                        Statement statement = connection.createStatement();
                        ResultSet rs = statement.executeQuery("SELECT A.appId, A.appDate, A.patientId, U.nameSurname, R.roomType\n" +
                                "FROM Appointment A, Doctor D, users U, Room R\n" +
                                "WHERE A.doctorId = D.userId \n" +
                                "and A.patientId = '" + activeUserId + "' \n" +
                                "AND A.doctorId = U.userId\n" +
                                "AND R.roomId = A.roomId\n" +
                                "AND A.appDate BETWEEN DATE_ADD(current_timestamp(),interval '" + -days + "' day) AND CURRENT_TIMESTAMP();");
                        while (rs.next()) {
                            appointments.add(new Appointment(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getString(4), rs.getString(5)));
                        }
                    }
                } else {
                    if (view.futureSelectionButton.isSelected()) {
                        Statement statement = connection.createStatement();
                        ResultSet rs = statement.executeQuery("SELECT A.appId, A.appDate, A.patientId, U.nameSurname, R.roomType\n" +
                                "FROM Appointment A, Doctor D, users U, Room R\n" +
                                "WHERE A.doctorId = D.userId \n" +
                                "and A.patientId = '" + activeUserId + "' \n" +
                                "AND A.doctorId = U.userId\n" +
                                "AND R.roomId = A.roomId\n" +
                                "and D.expertise = '" + expertise + "' \n" +
                                "AND A.appDate BETWEEN CURRENT_TIMESTAMP(); AND DATE_ADD(current_timestamp(),interval '" + days + "' day) ");
                        while (rs.next()) {
                            appointments.add(new Appointment(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getString(4), rs.getString(5)));
                        }
                    }
                    if (view.pastSelectionButton.isSelected()) {
                        Statement statement = connection.createStatement();
                        ResultSet rs = statement.executeQuery("SELECT A.appId, A.appDate, A.patientId, U.nameSurname, R.roomType\n" +
                                "FROM Appointment A, Doctor D, users U, Room R\n" +
                                "WHERE A.doctorId = D.userId \n" +
                                "and A.patientId = '" + activeUserId + "' \n" +
                                "AND A.doctorId = U.userId\n" +
                                "AND R.roomId = A.roomId\n" +
                                "and D.expertise = '" + expertise + "' \n" +
                                "AND A.appDate BETWEEN DATE_ADD(current_timestamp(),interval '" + -days + "' day) AND CURRENT_TIMESTAMP();");
                        while (rs.next()) {
                            appointments.add(new Appointment(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getString(4), rs.getString(5)));
                        }
                    }
                }
                view.appointmentListModel.removeAllElements();
                for (Appointment element:appointments) {
                    view.appointmentListModel.addElement(element);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view.patientFrame,"Please provide valid day number.");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (event.getSource() == view.searchDoctorsButton) {
            try {
                ArrayList<String> doctors = new ArrayList<>();
                String expertise = (String) view.expertiseBox1.getSelectedItem();
                if (expertise.equals("")){
                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery("SELECT U.nameSurname, D.expertise\n" +
                            "FROM Doctor D, users U\n" +
                            "WHERE D.userId = U.userId;");
                    while (rs.next()) {
                        doctors.add(new String(rs.getString(1) + ", " + rs.getString(2)));
                    }
                } else {
                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery("SELECT U.nameSurname, D.expertise\n" +
                            "FROM Doctor D, users U\n" +
                            "WHERE D.userId = U.userId AND D.expertise ='" + expertise + "';");
                    while (rs.next()) {
                        doctors.add(new String(rs.getString(1) + ", " + rs.getString(2)));
                    }
                }
                view.doctorsListModel.removeAllElements();
                for (String element:doctors) {
                    view.doctorsListModel.addElement(element);
                }
            } catch (Exception e) {
                e.printStackTrace();}
        }
        if (event.getSource() == view.filterDoctorAppointmentsButton) {
            try {
                int days = Integer.parseInt(view.daysTextField.getText());
                ArrayList<Appointment> appointments = new ArrayList<>();

                    if (view.futureSelectionButton.isSelected()) {
                        Statement statement = connection.createStatement();
                        ResultSet rs = statement.executeQuery("SELECT A.appId, A.appDate, A.patientId, U.nameSurname, R.roomType\n" +
                                "FROM Appointment A, Doctor D, users U, Room R\n" +
                                "WHERE A.doctorId = D.userId \n" +
                                "and A.doctorId = '" + activeUserId + "' \n" +
                                "AND A.patientId = U.userId\n" +
                                "AND R.roomId = A.roomId\n" +
                                "AND A.appDate BETWEEN CURRENT_TIMESTAMP() AND DATE_ADD(current_timestamp(),interval '-100' day);");
                        while (rs.next()) {
                            appointments.add(new Appointment(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getString(4), rs.getString(5)));
                        }
                    }
                    if (view.pastSelectionButton.isSelected()) {
                        Statement statement = connection.createStatement();
                        ResultSet rs = statement.executeQuery("SELECT A.appId, A.appDate, A.patientId, U.nameSurname, R.roomType\n" +
                                "FROM Appointment A, Doctor D, users U, Room R\n" +
                                "WHERE A.doctorId = D.userId \n" +
                                "and A.doctorId = '" + activeUserId + "' \n" +
                                "AND A.patientId = U.userId\n" +
                                "AND R.roomId = A.roomId\n" +
                                "AND A.appDate BETWEEN DATE_ADD(current_timestamp(),interval '" + -days + "' day) AND CURRENT_TIMESTAMP();");
                        while (rs.next()) {
                            appointments.add(new Appointment(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getString(4), rs.getString(5)));
                        }
                    }

                view.appointmentListModel.removeAllElements();
                for (Appointment element:appointments) {
                    view.appointmentListModel.addElement(element);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view.patientFrame,"Please provide valid day number.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (event.getSource() == view.submitButton){
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT MAX(durationId) FROM UnavailabilityDuration");
                resultSet.next();
                int durationId = resultSet.getInt(1) + 1 ;
                ArrayList<Unavailability> unavailabilities = new ArrayList<>();
                if (view.unavailableStartField.getText() != "" || view.unavailableEndField.getText() != ""){
                    java.sql.Date startDate = stringToDateWithHours(view.unavailableStartField.getText());
                    java.sql.Date endDate = stringToDateWithHours(view.unavailableEndField.getText());
                    statement.executeUpdate("INSERT INTO UnavailabilityDuration (durationId, userId, unavailableFrom, unavailableTo)\n" +
                            "VALUES \n" +
                            "    ('" + durationId + "', '" + activeUserId + "', '" + startDate + "', '" + endDate + "');");


                    ResultSet rs1 = statement.executeQuery("SELECT unavailableFrom, unavailableTo, userId FROM UnavailabilityDuration WHERE userId = '" + activeUserId + "';");
                    while (rs1.next()){
                        java.sql.Date uStartDate = rs1.getDate(1);
                        java.sql.Date uEndDate= rs1.getDate(2);
                        int doctorId = rs1.getInt(3);
                        unavailabilities.add(new Unavailability(doctorId,uStartDate,uEndDate,activeUserNameSurname));
                    }
                    view.doctorsUnavailabilityModel.removeAllElements();
                    for (Unavailability element:unavailabilities) {
                        view.doctorsUnavailabilityModel.addElement(element);
                    }
                } else {
                    ResultSet rs1 = statement.executeQuery("SELECT unavailableFrom, unavailableTo, userId FROM UnavailabilityDuration WHERE userId = '" + activeUserId + "';");
                    while (rs1.next()){
                        java.sql.Date uStartDate = rs1.getDate(1);
                        java.sql.Date uEndDate= rs1.getDate(2);
                        int doctorId = rs1.getInt(3);
                        unavailabilities.add(new Unavailability(doctorId,uStartDate,uEndDate,activeUserNameSurname));
                    }
                    view.doctorsUnavailabilityModel.removeAllElements();
                    for (Unavailability element:unavailabilities) {
                        view.doctorsUnavailabilityModel.addElement(element);
                    }
                }

            } catch (ParseException e){JOptionPane.showMessageDialog(view.doctorFrame,"Please provide valid dates");}
            catch (Exception e) {e.printStackTrace();}
        }
        if (event.getSource() == view.cancelButton){
            try {
                Statement statement = connection.createStatement();
                ArrayList<Unavailability> unavailabilities = new ArrayList<>();
                ResultSet rs1 = statement.executeQuery("SELECT unavailableFrom, unavailableTo, userId FROM UnavailabilityDuration WHERE userId = '" + activeUserId + "';");
                while (rs1.next()){
                    java.sql.Date uStartDate = rs1.getDate(1);
                    java.sql.Date uEndDate= rs1.getDate(2);
                    int doctorId = rs1.getInt(3);
                    unavailabilities.add(new Unavailability(doctorId,uStartDate,uEndDate,activeUserNameSurname));
                }
                view.doctorsUnavailabilityModel.removeAllElements();
                for (Unavailability element:unavailabilities) {
                    view.doctorsUnavailabilityModel.addElement(element);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (event.getSource() == view.adminSignupButton){
            try{
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT MAX(userId) FROM Users");
                resultSet.next();
                int userId = resultSet.getInt(1) + 1 ;
                String username = view.usernameField.getText();
                String nameSurname = view.nameSurnameField.getText();
                String userType = (String) view.medicalStaffTypeBox.getSelectedItem();
                char[] passwordChars = view.passwordField.getPassword();
                String password = new String(passwordChars);
                String encryptedPassword = encrypt(password,generateSecretKey());
                Date dateOfBirth = stringToDate(view.dateOfBirthField.getText());
                String gender = (String) view.genderBox.getSelectedItem();
                char g = 0;
                if (gender.equals("Male")){g = 'M';}
                else if (gender.equals("Female")) {g = 'F';}
                else {JOptionPane.showMessageDialog(view.loginframe,"Please select a gender");}
                int phoneNumber = Integer.parseInt(view.phoneNumberField.getText());
                String address = view.addressField.getText();
                statement.executeUpdate("INSERT INTO Users (userId, userType, username, " +
                        "nameSurname, userPassword, dateOfBirth, gender, " +
                        "phoneNumber, address) VALUES ('" + userId + "' , '" + userType + "' ,  '" + username + "' ,  '" + nameSurname + "' ,  '" + encryptedPassword + "' ,  '" + dateOfBirth + "' ,  '" + g + "' ,  '" + phoneNumber + "' ,  '" + address + "');");
                JOptionPane.showMessageDialog(view.adminFrame,"User successfully registered.");
            } catch (SQLIntegrityConstraintViolationException e) {
                JOptionPane.showMessageDialog(view.adminFrame,"This username already exists.");
            } catch (Exception e){
                JOptionPane.showMessageDialog(view.adminFrame,"Please provide valid values.");
                e.printStackTrace();
            }
        }
        if (event.getSource()== view.adminRefreshUsersButton){
            getUsers();
            view.adminUsersListModel.removeAllElements();
            for (User element:getUsers()) {
                view.adminUsersListModel.addElement(element);
            }
        }
        if (event.getSource() == view.deleteUserButton){
            try {
                int userId = ((User) view.adminUsersList.getSelectedValue()).getUserId();
                Statement statement = connection.createStatement();
                statement.executeUpdate("DELETE FROM Users WHERE userId = '" + userId + "';");
                getUsers();
                view.adminUsersListModel.removeAllElements();
                for (User element:getUsers()) {
                    view.adminUsersListModel.addElement(element);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT userId, userType, username, nameSurname, dateOfBirth, gender, phoneNumber, address FROM Users;");
            while (rs.next()){
                users.add(new User(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5),rs.getString(6),rs.getLong(7),rs.getString(8)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  users;
    }
    public Date stringToDate(String stringDate) throws ParseException {
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        Date newDate = new java.sql.Date(date.parse(stringDate).getTime());
        return newDate;
    }
    public java.sql.Date stringToDateWithHours(String stringDate) throws ParseException {
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date newDate = new java.sql.Date(date.parse(stringDate).getTime());
        return newDate;
    }

    public ArrayList<String> getExpertises() {
        try {
            ArrayList<String> expertises = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT expertise From Doctor");
            while (rs.next()) {
                expertises.add(rs.getString(1));
            }
            return expertises;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public SecretKey generateSecretKey() throws Exception {
        String password = "It's a secret!  Make sure it's long enough (24 bytes)";
        byte[] keyBytes = Arrays.copyOf(password.getBytes(utf8), 32);
        SecretKey key = new  SecretKeySpec(keyBytes, "AES");
        return key;
    }
    public  String encrypt(String plainText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        String encryptedMesssageBase64 = Base64.getEncoder().encodeToString(encryptedBytes);
        return encryptedMesssageBase64;
    }
    public  String decrypt(String encryptedBase64, SecretKey secretKey) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedBase64);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedText = new String(decryptedBytes, "UTF-8");
        return decryptedText;
    }
}