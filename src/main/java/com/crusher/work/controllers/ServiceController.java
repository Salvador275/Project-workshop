package com.crusher.work.controllers;

import com.crusher.work.entities.Service;
import com.crusher.work.exception.ResourceNotFoundException;
import com.crusher.work.repo.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ServiceController {
    @Autowired
    private ServiceRepository serviceRepository;

    @RequestMapping(value = "/services", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) //READ
    public Iterable<Service> getAllServices(){
        return serviceRepository.findAll();
    }

    @RequestMapping(value = "/services/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) //READ
    public ResponseEntity<Service> getServiceById(Long id) throws ResourceNotFoundException {
        Service service = serviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The service with this id="+ id +" was not found"));
        return ResponseEntity.ok().body(service);
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
