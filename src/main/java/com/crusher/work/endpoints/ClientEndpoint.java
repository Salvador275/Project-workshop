package com.crusher.work.endpoints;

import com.crusher.work.service.IService;
import soap_service.clients.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.crusher.work.entities.Client;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class ClientEndpoint {
    private static final String NAMESPACE_URI = "http://soap-service/clients";
    @Autowired
    private IService soapService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getClientByIdRequest")
    @ResponsePayload
    public GetClientByIdResponse getClient(@RequestPayload GetClientByIdRequest request) {
        GetClientByIdResponse response = new GetClientByIdResponse();
        ClientInfo clientInfo = new ClientInfo();
        BeanUtils.copyProperties(soapService.getClientById(request.getId()), clientInfo);
        response.setClient(clientInfo);
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllClientsRequest")
    @ResponsePayload
    public GetAllClientsResponse getAllClients() {
        GetAllClientsResponse response = new GetAllClientsResponse();
        List<ClientInfo> clientInfoList = new ArrayList<>();
        List<Client> clientList = soapService.getAllClients();
        for (int i = 0; i < clientList.size(); i++) {
            ClientInfo ob = new ClientInfo();
            BeanUtils.copyProperties(clientList.get(i), ob);
            clientInfoList.add(ob);
        }
        response.getClient().addAll(clientInfoList);
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addClientRequest")
    @ResponsePayload
    public AddClientResponse addClient(@RequestPayload AddClientRequest request) {
        AddClientResponse response = new AddClientResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        Client client = new Client();
        client.setName(request.getName());
        boolean flag = soapService.addClient(client);
        if (flag == false) {
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Content Already Available");
            response.setServiceStatus(serviceStatus);
        } else {
            ClientInfo clientInfo = new ClientInfo();
            BeanUtils.copyProperties(client, clientInfo);
            response.setClient(clientInfo);
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Added Successfully");
            response.setServiceStatus(serviceStatus);
        }
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateClientRequest")
    @ResponsePayload
    public UpdateClientResponse updateClient(@RequestPayload UpdateClientRequest request) {
        Client client = new Client();
        BeanUtils.copyProperties(request.getClient(), client);
        soapService.updateClient(client);
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode("SUCCESS");
        serviceStatus.setMessage("Content Updated Successfully");
        UpdateClientResponse response = new UpdateClientResponse();
        response.setServiceStatus(serviceStatus);
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteClientRequest")
    @ResponsePayload
    public DeleteClientResponse deleteClient(@RequestPayload DeleteClientRequest request) {
        Client client = soapService.getClientById(request.getId());
        ServiceStatus serviceStatus = new ServiceStatus();
        if (client == null ) {
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Content Not Available");
        } else {
            soapService.deleteClient(client);
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Deleted Successfully");
        }
        DeleteClientResponse response = new DeleteClientResponse();
        response.setServiceStatus(serviceStatus);
        return response;
    }
}
