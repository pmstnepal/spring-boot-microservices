package com.patient.controller;

import com.patient.model.Patient;
import com.patient.model.Service;
import com.patient.openfeign.ServiceClient;
import com.patient.service.PatientService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Log4j2
public class PatientController {
    private final PatientService patientService;
    private List<Patient> patients;
    private final ServiceClient serviceClient;

    @Autowired
    public PatientController(PatientService PatientService, ServiceClient serviceClient) {
        this.patientService = PatientService;
        this.serviceClient = serviceClient;
        patients = patientService.getAllPatients();
    }

    @GetMapping("/")
    public String viewHomePage() {
        log.info("Loading HomePage!!");
        return "index";
    }

    @GetMapping("/getAllPatients")
    public String getAllPatients(Model model) {
        log.info("Loading All Patients!!");
        model.addAttribute("patientsList", patients);
        return "patients";
    }

    @GetMapping("/getAllServices")
    @CircuitBreaker(name = "getAllServicesBreaker", fallbackMethod = "getAllServicesFallback")
    public String getAllServices(Model model) {
        log.info("Loading Available Services from Service Microservice!!");
        model.addAttribute("servicesList", serviceClient.getAllServices());
        return "services";
    }

    public String getAllServicesFallback(Model model, Exception ex) {
        log.info("Fallback method executed for getAllServices() since service is not responding!!");
        log.info("Exception message: {}", ex.getMessage());
        model.addAttribute("servicesList", new ArrayList<Service>());
        return "services";
    }

    @GetMapping("/registerNewPatient")
    public String registerNewPatient(Model model) {
        Patient patient = new Patient();
        Service service = new Service();
        log.info("Registering a new Patient!!");
        model.addAttribute("patient", patient);
        model.addAttribute("service", service);
        return "newPatient";
    }

    @PostMapping("/savePatient")
    public String savePatient(@ModelAttribute("patient") Patient patient, @ModelAttribute("service") Service service) {
        log.info("Registered Patient!!");
        patient.setServices(List.of(service));
        patients.add(patient);
        return "redirect:/";
    }

    @GetMapping("/addNewService")
    public String addNewService(Model model) {
        Service service = new Service();
        log.info("Adding a new Service!!");
        model.addAttribute("service", service);
        return "newService";
    }

    @PostMapping("/saveService")
    public String saveService(@ModelAttribute("service") Service service) {
        log.info("Calling Service Microservice for adding a new Service!!");
        String result = serviceClient.addNewService(service);
        log.info(result);
        return "redirect:/";
    }

    @GetMapping("/getLatestCount")
    public String getLatestCount(Model model) {
        log.info("Getting Services Count from Service Microservice!!");
        model.addAttribute("servicesCount", serviceClient.getServiceCount());
        model.addAttribute("patientsCount", patients.size());
        log.info("Getting Latest Counts!!");
        return "latestCount";
    }

    @GetMapping("/getByID")
    public String getByID(Model model) {
        Patient patient = new Patient();
        Service service = new Service();
        log.info("Get Patient/Service by ID!!");
        model.addAttribute("patient", patient);
        model.addAttribute("service", service);
        return "getByID";
    }

    @PostMapping("/getPatientByID")
    public String getPatientByID(@ModelAttribute("patient") Patient patient, Model model) {
        log.info("Get Patient by ID!!");
        model.addAttribute("patient", patients.stream().filter(p->p.getId().intValue() == patient.getId().intValue()).collect(Collectors.toList()));
        return "patientDetails";
    }

    @PostMapping("/getServiceByID")
    public String getServiceByID(@ModelAttribute("service") Service service, Model model) {
        log.info("Calling Service Microservice to get the Service by ID!!");
        Service serviceById = serviceClient.getServicesByID(service.getId());
        model.addAttribute("service", List.of(serviceById));
        return "serviceDetails";
    }

}
