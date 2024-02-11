package com.patient.service;

import com.patient.model.Patient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(1L, "Sunny", "Singh",
                List.of(new com.patient.model.Service(1L, "OPD", 500.00))));
        patients.add(new Patient(2L, "Rahul", "Bajaj",
                List.of(new com.patient.model.Service(1L, "OPD", 500.00),
                        new com.patient.model.Service(2L, "X-Ray", 300.00))));
        patients.add(new Patient(3L, "Aman", "Chopra",
                List.of(new com.patient.model.Service(3L, "ECG", 250.00))));
        patients.add(new Patient(4L, "Nikhil", "Sharma",
                List.of(new com.patient.model.Service(4L, "Blood Test", 800.00))));
        patients.add(new Patient(5L, "Arvind", "Kumar",
                List.of(new com.patient.model.Service(1L, "OPD", 500.00),
                        new com.patient.model.Service(5L, "USG", 1200.00))));
        return patients;
    }
}

