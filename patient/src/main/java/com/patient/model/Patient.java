package com.patient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Patient {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Service> services = new ArrayList<>();

    public String getAllServices() {
        return services.stream().map(s-> s.getServiceName()).collect(Collectors.joining(", "));
    }
}
