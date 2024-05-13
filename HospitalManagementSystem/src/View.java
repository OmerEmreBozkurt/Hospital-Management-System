import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class View extends JFrame {

    JFrame loginframe = new JFrame("Demo Hospital");
    JPanel loginPanel = new JPanel(new GridLayout(3,2));
    JLabel usernameLabel = new JLabel("Username:");
    JTextField usernameField = new JTextField();
    JLabel passwordLabel = new JLabel("Password:");
    JPasswordField passwordField = new JPasswordField();
    JButton OpenSignupButton = new JButton("Sign-up");
    JButton loginButton = new JButton("Log-in");


    JFrame signupFrame;
    JPanel signupPanel= new JPanel(new GridLayout(8, 2));
    JLabel nameSurnameLabel = new JLabel("Name Surname:");
    JTextField nameSurnameField = new JTextField();
    JLabel dobLabel = new JLabel("Date of Birth: (DD-MM-YYYY)");
    JTextField dateOfBirthField = new JTextField();
    JLabel genderLabel = new JLabel("Sex:");
    JComboBox genderBox = new JComboBox();
    JLabel phoneLabel = new JLabel("Phone Number:");
    JTextField phoneNumberField = new JTextField();
    JLabel addressLabel = new JLabel("Address:");
    JTextField  addressField = new JTextField();

    JButton signupButton = new JButton("Sign-Up");


    JFrame patientFrame;
    JFrame doctorFrame;
    JFrame nurseFrame;
    JFrame adminFrame;
    JTabbedPane patientTabbedPane = new JTabbedPane();
    JPanel patientAppointmentPanel = new JPanel();
    JPanel appointmentFilterPanel = new JPanel(new FlowLayout());
    DefaultListModel appointmentListModel = new DefaultListModel<>();
    JList appointmentList = new JList(appointmentListModel);
    JScrollPane appointmentScrollPane = new JScrollPane();
    JLabel daysLabel = new JLabel("Number of Days");
    JTextField daysTextField = new JTextField(5);
    JButton filterAppointmentsButton = new JButton("Filter Appointments");
    JRadioButton pastSelectionButton = new JRadioButton("Past");
    JRadioButton futureSelectionButton = new JRadioButton("Future");
    JPanel patientDoctorsPanel = new JPanel();
    JPanel doctorsFilterPanel = new JPanel(new GridLayout(4,2));
    DefaultComboBoxModel expertiseComboBoxModel = new DefaultComboBoxModel();
    JLabel expertiseLabel1 = new JLabel("Expertise:");
    JComboBox expertiseBox1 = new JComboBox(expertiseComboBoxModel);
    JLabel expertiseLabel2 = new JLabel("Expertise:");
    JComboBox expertiseBox2 = new JComboBox(expertiseComboBoxModel);
    JLabel specificDayLabel = new JLabel("Specific Day (YYYY-MM-DD):");
    JTextField specificDayField = new JTextField();
    JLabel startDateLabel = new JLabel("Start Date (YYYY-MM-DD):");
    JTextField startDateField = new JTextField();
    JLabel endDateLabel = new JLabel("End Date (YYYY-MM-DD):");
    JTextField endDateField = new JTextField();
    DefaultListModel doctorsListModel = new DefaultListModel<>();
    JList doctorsList = new JList(doctorsListModel);
    JScrollPane doctorsScrollPane = new JScrollPane();
    JPanel searchButtonPanel = new JPanel(new FlowLayout());
    JButton searchDoctorsButton = new JButton("Search");
    JButton makeAppointmentButton = new JButton("Make Appointment");

    JTabbedPane doctorTabbedPane = new JTabbedPane();
    JPanel doctorAppointmentPanel = new JPanel();
    JPanel doctorAvailabilityPanel = new JPanel();
    JPanel formPanel = new JPanel(new GridLayout(3, 2));
    JLabel unavailableStartLabel = new JLabel("Unavailability Start (dd-MM-yyyy):");
    JTextField unavailableStartField = new JTextField();
    JLabel unavailableEndLabel = new JLabel("Unavailability End (dd-MM-yyyy):");
    JTextField unavailableEndField = new JTextField();
    JButton submitButton = new JButton("Submit");
    JButton cancelButton = new JButton("Refresh");
    DefaultListModel doctorsUnavailabilityModel = new DefaultListModel<>();
    JList doctorsUnavailabilityList = new JList(doctorsUnavailabilityModel);
    JScrollPane doctorsUnavailabilityPane = new JScrollPane();

    JPanel doctorRoomPanel = new JPanel();
    JPanel listsPanel = new JPanel(new GridLayout(1,2));
    DefaultListModel roomListModel = new DefaultListModel<>();
    JList roomList = new JList(roomListModel);
    JScrollPane roomScrollPane = new JScrollPane();
    JList doctorsAppointmentList = new JList(appointmentListModel);
    JScrollPane doctorsAppointmentPane = new JScrollPane();
    JPanel roomButtonsPanel = new JPanel(new FlowLayout());
    JButton assignButton = new JButton("Assign");
    JButton unassignButton = new JButton("Unassign");
    JPanel nurseRoomAndAppointmentPanel = new JPanel(new GridLayout(1,2));
    JList nurseAppointmentList = new JList(appointmentListModel);
    JScrollPane nurseAppointmentPane = new JScrollPane();
    JButton refreshButton = new JButton("Refresh");
    JPanel nurseInfoPanel = new JPanel(new GridLayout(1,2));

    JTabbedPane adminTabbedPane = new JTabbedPane();
    JPanel adminSignupPanel = new JPanel();
    JButton adminSignupButton = new JButton("Register User");
    JLabel medicalStaffTypeLabel = new JLabel("Medical Staff Type");
    JComboBox medicalStaffTypeBox = new JComboBox();
    JPanel adminUsersPanel = new JPanel();
    DefaultListModel adminUsersListModel = new DefaultListModel<>();
    JList adminUsersList = new JList(adminUsersListModel);
    JScrollPane adminUsersPane = new JScrollPane();
    JPanel adminButtonsPanel = new JPanel(new GridLayout(1,2));
    JButton adminRefreshUsersButton = new JButton("Refresh");
    JButton deleteUserButton = new JButton("Delete User");
    JPanel adminStatisticsPanel = new JPanel();
    JButton filterDoctorAppointmentsButton = new JButton("Filter Appointments");






    public View(){
        setupFrame();
    }

    public void setupFrame(){
        loginframe.setDefaultCloseOperation(HIDE_ON_CLOSE);
        loginframe.setSize(400,200);
        loginframe.setLayout(new BorderLayout());
        setupLoginPanel();

        loginframe.setVisible(true);
    }

    public void setupLoginPanel(){
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(OpenSignupButton);
        loginPanel.add(loginButton);

        loginframe.add(loginPanel, BorderLayout.CENTER);
    }

    public void setupSignupFrame(){
        signupFrame = new JFrame("Sign-up");
        signupFrame.setSize(400,600);
        signupFrame.setLayout(new BorderLayout());

        signupPanel.removeAll();
        signupPanel.add(usernameLabel);
        signupPanel.add(usernameField);
        signupPanel.add(nameSurnameLabel);
        signupPanel.add(nameSurnameField);
        signupPanel.add(passwordLabel);
        signupPanel.add(passwordField);
        signupPanel.add(dobLabel);
        signupPanel.add(dateOfBirthField);
        signupPanel.add(genderLabel);
        genderBox.addItem(new String("Male"));
        genderBox.addItem(new String("Female"));
        signupPanel.add(genderBox);
        signupPanel.add(phoneLabel);
        signupPanel.add(phoneNumberField);
        signupPanel.add(addressLabel);
        signupPanel.add(addressField);
        signupPanel.add(new JLabel());
        signupPanel.add(signupButton);

        signupFrame.add(signupPanel, BorderLayout.CENTER);
        signupFrame.setVisible(true);
    }

    public void setupPatientFrame(int userId, String nameSurname){
        patientFrame = new JFrame("Patient " + userId + ", " + nameSurname);
        patientFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        patientFrame.setSize(800, 600);
        patientFrame.setLayout(new BorderLayout());
        patientTabbedPane.setFocusable(false);
        patientFrame.add(patientTabbedPane);

        setupPatientAppointmentPanel();
        patientTabbedPane.add("Appointments", patientAppointmentPanel);

        setupPatientDoctorsPanel();
        patientTabbedPane.add("Doctors", patientDoctorsPanel);

        patientFrame.setVisible(true);
    }
    public void setupPatientAppointmentPanel(){
        patientAppointmentPanel.setLayout(new BorderLayout());
        appointmentFilterPanel.add(daysLabel);
        appointmentFilterPanel.add(daysTextField);
        appointmentFilterPanel.add(pastSelectionButton);
        appointmentFilterPanel.add(futureSelectionButton);
        appointmentFilterPanel.add(expertiseLabel1);
        expertiseBox1.addItem(new String(""));
        appointmentFilterPanel.add(expertiseBox1);
        appointmentFilterPanel.add(filterAppointmentsButton);

        appointmentList.setLayoutOrientation(JList.VERTICAL);
        appointmentScrollPane.setViewportView(appointmentList);


        patientAppointmentPanel.add(appointmentFilterPanel, BorderLayout.NORTH);
        patientAppointmentPanel.add(appointmentScrollPane, BorderLayout.CENTER);
    }
    public void setupPatientDoctorsPanel(){
        patientDoctorsPanel.setLayout(new BorderLayout());
        doctorsFilterPanel.add(expertiseLabel2);
        doctorsFilterPanel.add(expertiseBox2);
        doctorsFilterPanel.add(specificDayLabel);
        doctorsFilterPanel.add(specificDayField);
        doctorsFilterPanel.add(startDateLabel);
        doctorsFilterPanel.add(startDateField);
        doctorsFilterPanel.add(endDateLabel);
        doctorsFilterPanel.add(endDateField);
        doctorsList.setLayoutOrientation(JList.VERTICAL);
        doctorsScrollPane.setViewportView(doctorsList);

        searchButtonPanel.add(searchDoctorsButton);
        searchButtonPanel.add(makeAppointmentButton);

        patientDoctorsPanel.add(doctorsFilterPanel, BorderLayout.NORTH);
        patientDoctorsPanel.add(doctorsScrollPane, BorderLayout.CENTER);
        patientDoctorsPanel.add(searchButtonPanel, BorderLayout.SOUTH);
    }

    public void setupDoctorFrame(int userId, String nameSurname){
        doctorFrame = new JFrame("Doctor " + userId + ", " + nameSurname);
        doctorFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        doctorFrame.setSize(800, 600);
        doctorFrame.setLayout(new BorderLayout());
        doctorTabbedPane.setFocusable(false);
        doctorFrame.add(doctorTabbedPane);

        setupDoctorAppointmentPanel();
        doctorTabbedPane.add("Appointments", doctorAppointmentPanel);
        setupDoctorAvailabilityPanel();
        doctorTabbedPane.add("Availability", doctorAvailabilityPanel);
        setupDoctorRoomPanel();
        doctorTabbedPane.add("Rooms", doctorRoomPanel);

        doctorFrame.setVisible(true);
    }
    public void setupDoctorAppointmentPanel(){
        doctorAppointmentPanel.setLayout(new BorderLayout());
        appointmentFilterPanel.add(daysLabel);
        appointmentFilterPanel.add(daysTextField);
        appointmentFilterPanel.add(pastSelectionButton);
        appointmentFilterPanel.add(futureSelectionButton);
        appointmentFilterPanel.add(filterDoctorAppointmentsButton);

        appointmentList.setLayoutOrientation(JList.VERTICAL);
        appointmentScrollPane.setViewportView(appointmentList);

        doctorAppointmentPanel.add(appointmentFilterPanel, BorderLayout.NORTH);
        doctorAppointmentPanel.add(appointmentScrollPane, BorderLayout.CENTER);
    }
    public void setupDoctorAvailabilityPanel(){
        doctorAvailabilityPanel.setLayout(new BorderLayout());
        formPanel.add(unavailableStartLabel);
        formPanel.add(unavailableStartField);
        formPanel.add(unavailableEndLabel);
        formPanel.add(unavailableEndField);
        formPanel.add(cancelButton);
        formPanel.add(submitButton);

        doctorsUnavailabilityList.setLayoutOrientation(JList.VERTICAL);
        doctorsUnavailabilityPane.setViewportView(doctorsUnavailabilityList);

        doctorAvailabilityPanel.add(formPanel, BorderLayout.NORTH);
        doctorAvailabilityPanel.add(doctorsUnavailabilityPane, BorderLayout.CENTER);
    }
    public void setupDoctorRoomPanel(){
        doctorRoomPanel.setLayout(new BorderLayout());
        doctorsAppointmentPane.setViewportView(doctorsAppointmentList);
        listsPanel.add(doctorsAppointmentPane);
        roomScrollPane.setViewportView(roomList);
        listsPanel.add(roomScrollPane);

        roomButtonsPanel.add(unassignButton);
        roomButtonsPanel.add(assignButton);

        doctorRoomPanel.add(listsPanel, BorderLayout.CENTER);
        doctorRoomPanel.add(roomButtonsPanel, BorderLayout.SOUTH);
    }


    public void setupNurseFrame(int userId, String nameSurname){
        nurseFrame = new JFrame("Nurse " + userId + ", " + nameSurname);
        nurseFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        nurseFrame.setSize(800, 600);
        nurseFrame.setLayout(new BorderLayout());

        setupNurseRoomAndAppointmentPanel();
        nurseFrame.add(nurseRoomAndAppointmentPanel, BorderLayout.CENTER);
        nurseFrame.add(refreshButton, BorderLayout.SOUTH);
        nurseFrame.add(nurseInfoPanel, BorderLayout.NORTH);

        nurseFrame.setVisible(true);
    }
    public void setupNurseRoomAndAppointmentPanel(){
        roomScrollPane.setViewportView(roomList);
        nurseRoomAndAppointmentPanel.add(roomScrollPane);
        nurseAppointmentPane.setViewportView(nurseAppointmentList);
        nurseRoomAndAppointmentPanel.add(nurseAppointmentPane);

        nurseInfoPanel.add(new JLabel("Rooms:"));
        nurseInfoPanel.add(new JLabel("Upcoming Appointments:"));
    }

    public void setupAdminFrame(int userId, String nameSurname){
        adminFrame = new JFrame("Admin " + userId + ", " + nameSurname);
        adminFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        adminFrame.setSize(800, 600);
        adminFrame.setLayout(new BorderLayout());
        adminTabbedPane.setFocusable(false);
        adminFrame.add(adminTabbedPane);

        setupAdminStatisticsPanel();
        adminTabbedPane.add("Statistics", adminStatisticsPanel);
        setupAdminSignupPanel();
        adminTabbedPane.add("Medical Staff Register", adminSignupPanel);
        setupAdminUsersPanel();
        adminTabbedPane.add("Users", adminUsersPanel);

        adminFrame.setVisible(true);
    }
    public void setupAdminStatisticsPanel(){
        adminStatisticsPanel.setLayout(new FlowLayout());
    }
    public void setupAdminSignupPanel(){
        adminSignupPanel.setLayout(new GridLayout(9,2));

        adminSignupPanel.removeAll();
        adminSignupPanel.add(nameSurnameLabel);
        adminSignupPanel.add(nameSurnameField);
        adminSignupPanel.add(usernameLabel);
        adminSignupPanel.add(usernameField);
        adminSignupPanel.add(passwordLabel);
        adminSignupPanel.add(passwordField);
        adminSignupPanel.add(medicalStaffTypeLabel);
        adminSignupPanel.add(medicalStaffTypeBox);
        medicalStaffTypeBox.addItem("Doctor");
        medicalStaffTypeBox.addItem("Nurse");
        medicalStaffTypeBox.addItem("Manager");
        adminSignupPanel.add(dobLabel);
        adminSignupPanel.add(dateOfBirthField);
        adminSignupPanel.add(genderLabel);
        genderBox.addItem(new String("Male"));
        genderBox.addItem(new String("Female"));
        adminSignupPanel.add(genderBox);
        adminSignupPanel.add(phoneLabel);
        adminSignupPanel.add(phoneNumberField);
        adminSignupPanel.add(addressLabel);
        adminSignupPanel.add(addressField);
        adminSignupPanel.add(new JLabel());
        adminSignupPanel.add(adminSignupButton);
    }
    public void setupAdminUsersPanel(){
        adminUsersPanel.setLayout(new BorderLayout());

        adminUsersPane.setViewportView(adminUsersList);
        adminUsersPanel.add(adminUsersPane, BorderLayout.CENTER);
        adminUsersPanel.add(adminButtonsPanel, BorderLayout.SOUTH);
        adminButtonsPanel.add(adminRefreshUsersButton);
        adminButtonsPanel.add(deleteUserButton);
    }

    public void addActionListener(ActionListener actionListener){
        OpenSignupButton.addActionListener(actionListener);
        loginButton.addActionListener(actionListener);
        signupButton.addActionListener(actionListener);
        filterAppointmentsButton.addActionListener(actionListener);
        searchDoctorsButton.addActionListener(actionListener);
        filterDoctorAppointmentsButton.addActionListener(actionListener);
        submitButton.addActionListener(actionListener);
        cancelButton.addActionListener(actionListener);
        adminSignupButton.addActionListener(actionListener);
        adminRefreshUsersButton.addActionListener(actionListener);
        deleteUserButton.addActionListener(actionListener);
    }
}
