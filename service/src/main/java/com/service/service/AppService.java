package com.service.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppService {
    public List<com.service.model.Service> getAllServices() {
        List<com.service.model.Service> services = new ArrayList<>();
        services.add(new com.service.model.Service(1L, "OPD", 500.00));
        services.add(new com.service.model.Service(2L, "X-Ray", 300.00));
        services.add(new com.service.model.Service(3L, "ECG", 250.00));
        services.add(new com.service.model.Service(4L, "Blood Test", 800.00));
        services.add(new com.service.model.Service(5L, "USG", 1200.00));
        services.add(new com.service.model.Service(6L, "Vaccination", 450.00));
        return services;
    }
}
