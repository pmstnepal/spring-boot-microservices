package com.patient.openfeign;

import com.patient.model.Service;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

//@FeignClient(url = "http://localhost:8082", value = "service-microservice")
@FeignClient(name = "SERVICE-MICROSERVICE")
public interface ServiceClient {

    @GetMapping("/getAllServices")
    List<Service> getAllServices();

    @GetMapping("/getServiceByID/{id}")
    Service getServicesByID(@PathVariable("id") Long id);

    @PostMapping("/addNewService")
    String addNewService(@RequestBody Service service);

    @GetMapping("/getServiceCount")
    String getServiceCount();

}
