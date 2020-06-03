package com.crusher.work.controllers;

import com.crusher.work.entities.Application;
import com.crusher.work.entities.Client;
import com.crusher.work.entities.Service;
import com.crusher.work.exception.ResourceNotFoundException;
import com.crusher.work.repo.ApplicationRepository;
import com.crusher.work.repo.ClientRepository;
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
public class ApplicationController {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @RequestMapping(value = "/applications", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllAplications(Model model){
        Iterable<Application> applications = applicationRepository.findAll();
        ArrayList<Application> applicationsList = new ArrayList<Application>();
        for (Application application : applications){
            applicationsList.add(application);
        }
        model.addAttribute("elements", applicationsList);
        model.addAttribute("title", "Applications");
        return "application";
    }

    @RequestMapping(value = "/applications/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getApplicationById(Long id, Model model) throws ResourceNotFoundException{
        Application application = applicationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The application with this id="+ id +" was not found"));
        model.addAttribute("elements", application);
        model.addAttribute("title", "Applications");
        return "application";
    }

    @RequestMapping(value = "/applications/create", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) // CREATE
    public Application createApplication(Long client_id, Long service_id) throws ResourceNotFoundException{
        Client client = clientRepository.findById(client_id).orElseThrow(() -> new ResourceNotFoundException(("The client with this id="+ client_id +" was not found")));
        Service service = serviceRepository.findById(service_id).orElseThrow(() -> new ResourceNotFoundException(("The service with this id="+ service_id +" was not found")));

        Application application = new Application();
        application.setClient(client);
        application.setService(service);

        return applicationRepository.save(application);
    }

    @RequestMapping(value = "/applications/update", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) //UPDATE
    public ResponseEntity<Application> updateApplication(Long id, Long client_id, Long service_id) throws ResourceNotFoundException {
        Client client = clientRepository.findById(client_id).orElseThrow(() -> new ResourceNotFoundException(("The client with this id="+ client_id +" was not found")));
        Service service = serviceRepository.findById(service_id).orElseThrow(() -> new ResourceNotFoundException(("The service with this id="+ service_id +" was not found")));

        Application application = applicationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The application with this id=" + id + " was not found")
        );
        application.setClient(client);
        application.setService(service);
        final Application updateApplication = applicationRepository.save(application);
        return ResponseEntity.ok(updateApplication);
    }

    @RequestMapping(value = "/applications/delete", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) //DELETE
    public Map<String, Boolean> deleteApplication(Long id) throws ResourceNotFoundException {
        Application application = applicationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The application with this id="+ id +" was not found")
        );
        applicationRepository.delete(application);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
