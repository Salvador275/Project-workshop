package com.crusher.work.service;

import com.crusher.work.entities.Application;
import com.crusher.work.entities.Client;
import com.crusher.work.entities.Service;
import com.crusher.work.repositories.ApplicationRepository;
import com.crusher.work.repositories.ClientRepository;
import com.crusher.work.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class SoapService implements IService {
    //Client
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        clientRepository.findAll().forEach(e -> clients.add(e));
        return clients;
    }

    @Override
    public Client getClientById(Long id) {
        Client client = clientRepository.findClientById(id);
        return client;
    }

    @Override
    public synchronized boolean addClient(Client client) {
        Client clientRequest = clientRepository.findClientById(client.getId());
        if (clientRequest == null){
            clientRepository.save(client);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void updateClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }
    //Client

    //Service
    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public List<Service> getAllServices() {
        List<Service> services = new ArrayList<>();
        serviceRepository.findAll().forEach(e -> services.add(e));
        return services;
    }

    @Override
    public Service getServiceById(Long id) {
        Service service = serviceRepository.findServiceById(id);
        return service;
    }

    @Override
    public boolean addService(Service service) {
        Service serviceRequest = serviceRepository.findServiceById(service.getId());
        if (serviceRequest == null){
            serviceRepository.save(service);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void updateService(Service service) {
        serviceRepository.save(service);
    }

    @Override
    public void deleteService(Service service) {
        serviceRepository.delete(service);
    }
    //Service

    //Application
    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public List<Application> getAllApplications() {
        List<Application> applications = new ArrayList<>();
        applicationRepository.findAll().forEach(e -> applications.add(e));
        return applications;
    }

    @Override
    public Application getApplicationById(Long id) {
        Application application = applicationRepository.findApplicationById(id);
        return application;
    }

    @Override
    public boolean addApplication(Application application) {
        Application applicationRequest = applicationRepository.findApplicationById(application.getId());
        if (applicationRequest == null){
            applicationRepository.save(application);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void updateApplication(Application application) {
        applicationRepository.save(application);
    }

    @Override
    public void deleteApplication(Application application) {
        applicationRepository.delete(application);
    }
    //Application
}
