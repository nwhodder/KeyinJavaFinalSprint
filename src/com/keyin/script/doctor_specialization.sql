CREATE TABLE doctor_specialization (
    doctor_id INT PRIMARY KEY,
	medical_license INT NOT NULL,
    specialization VARCHAR(50) NOT NULL,
    FOREIGN KEY (doctor_id) REFERENCES users(id)
)