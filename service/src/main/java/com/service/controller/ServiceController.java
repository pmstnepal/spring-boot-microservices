package com.service.controller;

import com.service.model.Service;
import com.service.service.AppService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
public class ServiceController {
    private final AppService appService;
    private List<Service> services;

    @Autowired
    public ServiceController(AppService appService) {
        this.appService = appService;
        services = appService.getAllServices();
    }

    @GetMapping("/")
    public String viewHomePage() {
        log.info("Loading HomePage!!");
        return "Welcome to Service Microservice!!";
    }

    @GetMapping("/getAllServices")
    public List<Service> getAllServices() {
        log.info("Loading Available Services!!");
        return services;
    }

    @PostMapping("/addNewService")
    public String addNewService(@RequestBody Service service) {
        log.info("Adding a New Service!!");
        services.add(service);
        return "Successfully Added a New Service";
    }

    @GetMapping("/getServiceCount")
    public Integer getServiceCount() {
        log.info("Getting Latest Count!!");
        return services.size();
    }

    @GetMapping("/getServiceByID/{id}")
    public Service getServiceByID(@PathVariable Long id) {
        log.info("Getting Service by ID!!");
        return services.stream().filter(p->p.getId().intValue() == id.intValue()).findFirst().orElse(null);
    }
}
