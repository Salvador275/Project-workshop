package com.crusher.work.controllers;

import com.crusher.work.entities.Client;
import com.crusher.work.exception.ResourceNotFoundException;
import com.crusher.work.repo.ClientRepository;
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
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(value = "/clients", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) //READ
    public String getAllClients(Model model){
        Iterable<Client> clients = clientRepository.findAll();
        ArrayList<Client> clientsList = new ArrayList<Client>();
        for (Client client : clients){
            clientsList.add(client);
        }
        model.addAttribute("elements", clientsList);
        model.addAttribute("title", "Clients");
        return "elements";
    }

    @RequestMapping(value = "/clients/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) //READ
    public String getClientById(Long id, Model model) throws ResourceNotFoundException {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The client with this id="+ id +" was not found"));
        model.addAttribute("element", client);
        model.addAttribute("title", "Clients");
        return "detail";
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
