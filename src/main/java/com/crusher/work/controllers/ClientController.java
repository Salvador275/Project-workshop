package com.crusher.work.controllers;

import com.crusher.work.entities.Client;
import com.crusher.work.exception.ResourceNotFoundException;
import com.crusher.work.repo.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(value = "/clients", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) //READ
    public Iterable<Client> getAllClients(){
        return clientRepository.findAll();
    }

    @RequestMapping(value = "/clients/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) //READ
    public ResponseEntity<Client> getClientById(Long id) throws ResourceNotFoundException {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The client with this id="+ id +" was not found"));
        return ResponseEntity.ok().body(client);
    }

    @RequestMapping(value = "/clients/create", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) // CREATE
    public Client createClient(String name){
        Client client = new Client();
        client.setName(name);

        return clientRepository.save(client);
    }

    @RequestMapping(value = "/clients/update", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) //UPDATE
    public ResponseEntity<Client> updateClient(Long id, String name) throws ResourceNotFoundException {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The client with this id="+ id +" was not found")
        );
        client.setName(name);
        final Client updateClient = clientRepository.save(client);
        return ResponseEntity.ok(updateClient);
    }

    @RequestMapping(value = "/clients/delete", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) //DELETE
    public Map<String, Boolean> deleteClient(Long id) throws ResourceNotFoundException {
        Client client = clientRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("The client with this id="+ id +" was not found")
            );
        clientRepository.delete(client);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
