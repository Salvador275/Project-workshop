package com.crusher.work.service;

import com.crusher.work.entities.Application;
import com.crusher.work.entities.Client;
import com.crusher.work.entities.Service;

import java.util.List;

public interface IService {
    List<Client> getAllClients();
    Client getClientById(Long id);
    boolean addClient(Client client);
    void updateClient(Client client);
    void deleteClient(Client client);

    List<Service> getAllServices();
    Service getServiceById(Long id);
    boolean addService(Service service);
    void updateService(Service service);
    void deleteService(Service service);

    List<Application> getAllApplications();
    Application getApplicationById(Long id);
    boolean addApplication(Application application);
    void updateApplication(Application application);
    void deleteApplication(Application application);
}
