package com.crusher.work.controllers;

import com.crusher.work.entities.Service;
import com.crusher.work.exception.ResourceNotFoundException;
import com.crusher.work.repo.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ServiceController {
    @Autowired
    private ServiceRepository serviceRepository;

    @RequestMapping(value = "/services", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) //READ
    public String getAllServices(Model model){
        Iterable<Service> services = serviceRepository.findAll();
        ArrayList<Service> servicesList = new ArrayList<Service>();
        for (Service service : services){
            servicesList.add(service);
        }
        model.addAttribute("elements", servicesList);
        model.addAttribute("title", "Services");
        return "elements";
    }

    @RequestMapping(value = "/services/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) //READ
    public String getServiceById(Long id, Model model) throws ResourceNotFoundException {
        Service service = serviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The service with this id="+ id +" was not found"));
        model.addAttribute("elements", service);
        model.addAttribute("title", "Services");
        return "elements";
    }

    @RequestMapping(value = "/services/create", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) // CREATE
    public Service createService(String name){
        Service service = new Service();
        service.setName(name);

        return serviceRepository.save(service);
    }

    @RequestMapping(value = "/services/update", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) //UPDATE
    public ResponseEntity<Service> updateService(Long id, String name) throws ResourceNotFoundException {
        Service service = serviceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The service with this id="+ id +" was not found")
        );
        service.setName(name);
        final Service updateService = serviceRepository.save(service);
        return ResponseEntity.ok(updateService);
    }

    @RequestMapping(value = "/services/delete", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) //DELETE
    public Map<String, Boolean> deleteService(Long id) throws ResourceNotFoundException {
        Service service = serviceRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("The service with this id="+ id +" was not found")
            );
        serviceRepository.delete(service);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
