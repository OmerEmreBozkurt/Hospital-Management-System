use cs202_project;

INSERT INTO Users (userId, userType, username, nameSurname, userPassword, dateOfBirth, gender, phoneNumber, address)
VALUES 
    (1, 'Manager', 'admin123', 'John Doe', '/S32UtuhWpZtznBz+JdKnQ==', '1980-05-15', 'M', '1234567890', '123 Admin St'),
    (2, 'Patient', 'patient1', 'Alice Smith', '/S32UtuhWpZtznBz+JdKnQ==', '1995-08-20', 'F', '9876543210', '456 Patient St'),
    (3, 'Doctor', 'doctor1', 'Dr. Michael Johnson', '/S32UtuhWpZtznBz+JdKnQ==', '1976-12-10', 'M', '5551234567', '789 Doctor St'),
    (4, 'Nurse', 'nurse1', 'Emily Brown', '/S32UtuhWpZtznBz+JdKnQ==', '1990-03-25', 'F', '7778889999', '321 Nurse St'),
    (5, 'Patient', 'patient2', 'Bob Jones', '/S32UtuhWpZtznBz+JdKnQ==', '1988-07-02', 'M', '5557778888', '789 Patient St'),
	(6, 'Patient', 'patient3', 'Eva Martinez', '/S32UtuhWpZtznBz+JdKnQ==', '1988-09-27', 'F', '7777777777', '1010 Avenue A'),
	(7, 'Doctor', 'doctor2', 'Dr. Sarah Lee', '/S32UtuhWpZtznBz+JdKnQ==', '1985-07-08', 'F', '8888888888', '222 Medical St'),
    (8, 'Doctor', 'doctor3', 'Dr. Robert Garcia', '/S32UtuhWpZtznBz+JdKnQ==', '1970-12-01', 'M', '9999999999', '333 Health Ave'),
    (9, 'Nurse', 'nurse2', 'Emma Davis', '/S32UtuhWpZtznBz+JdKnQ==', '1993-02-14', 'F', '1111111111', '456 Care Blvd'),
    (10, 'Nurse', 'nurse3', 'James Wilson', '/S32UtuhWpZtznBz+JdKnQ==', '1983-06-30', 'M', '2222222222', '789 Healing St');
-- all passwords are '123' and encrypted. New users passwords will be encrypted. 

INSERT INTO Patient (userId, bloodType)
VALUES 
    (2, 'AB+'),
    (5, 'O-'),
    (6, 'A+');

INSERT INTO Doctor (userId, expertise)
VALUES 
	(3, 'Cardiology'),
    (7, 'Neurology'),
    (8, 'Pediatrics');
    
INSERT INTO UnavailabilityDuration (durationId, userId, unavailableFrom, unavailableTo)
VALUES 
    (1, 7, '2023-12-25 08:00:00', '2023-12-25 12:00:00'),
    (2, 8, '2023-12-30 14:00:00', '2023-12-31 18:00:00'),
    (3, 3, '2024-01-02 09:00:00', '2024-01-02 15:00:00'), 
    (4, 7, '2024-01-04 12:00:00', '2024-01-04 16:00:00'); 

INSERT INTO Nurse (userId)
VALUES 
	(4),
    (9),
    (10);

INSERT INTO Manager (userId)
VALUES (1);

INSERT INTO Room (roomId, nurseId, roomType, isAvailable)
VALUES 
    (1, 4, 'Injection Room', false),
    (2, NULL, 'General Checkup Room', true),
    (3, 10, 'Pediatrics Room', false),
    (4, NULL, 'X-Ray Room', true),
    (5, NULL,  'OR', true),
    (6, NULL, 'Intensive care unit', true),
    (7, NULL, 'Medical-surgical room', true),
    (8, NULL, 'Behavioral and mental health room', true);
    
INSERT INTO Appointment (appId, appDate, patientId, doctorId, roomId)
VALUES 
    (1, '2023-12-25 10:00:00', 2, 7, null),
    (2, '2023-12-26 14:30:00', 5, 3, null),
    (3, '2023-12-27 11:45:00', 2, 3, 1),
    (4, '2023-12-28 09:15:00', 6, 8, 3),
    (5, '2023-12-29 16:00:00', 2, 7, null),
    (6, '2024-12-29 16:00:00', 2, 7, null),
    (7, '2025-12-29 16:00:00', 2, 7, null),
    (8, '2026-12-29 16:00:00', 2, 7, null);


