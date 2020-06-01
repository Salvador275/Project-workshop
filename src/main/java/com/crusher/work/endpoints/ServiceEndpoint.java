package com.crusher.work.endpoints;

import com.crusher.work.entities.Service;
import com.crusher.work.service.IService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap_service.services.*;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class ServiceEndpoint {
    private static final String NAMESPACE_URI = "http://soap-service/services";
    @Autowired
    private IService soapService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getServiceByIdRequest")
    @ResponsePayload
    public GetServiceByIdResponse getService(@RequestPayload GetServiceByIdRequest request) {
        GetServiceByIdResponse response = new GetServiceByIdResponse();
        ServiceInfo serviceInfo = new ServiceInfo();
        BeanUtils.copyProperties(soapService.getServiceById(request.getId()), serviceInfo);
        response.setService(serviceInfo);
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllServicesRequest")
    @ResponsePayload
    public GetAllServicesResponse getAllServices() {
        GetAllServicesResponse response = new GetAllServicesResponse();
        List<ServiceInfo> serviceInfoList = new ArrayList<>();
        List<Service> serviceList = soapService.getAllServices();
        for (int i = 0; i < serviceList.size(); i++) {
            ServiceInfo ob = new ServiceInfo();
            BeanUtils.copyProperties(serviceList.get(i), ob);
            serviceInfoList.add(ob);
        }
        response.getService().addAll(serviceInfoList);
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addServiceRequest")
    @ResponsePayload
    public AddServiceResponse addService(@RequestPayload AddServiceRequest request) {
        AddServiceResponse response = new AddServiceResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        Service service = new Service();
        service.setName(request.getName());
        boolean flag = soapService.addService(service);
        if (flag == false) {
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Content Already Available");
            response.setServiceStatus(serviceStatus);
        } else {
            ServiceInfo serviceInfo = new ServiceInfo();
            BeanUtils.copyProperties(service, serviceInfo);
            response.setService(serviceInfo);
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Added Successfully");
            response.setServiceStatus(serviceStatus);
        }
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateServiceRequest")
    @ResponsePayload
    public UpdateServiceResponse updateService(@RequestPayload UpdateServiceRequest request) {
        Service service = new Service();
        BeanUtils.copyProperties(request.getService(), service);
        soapService.updateService(service);
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode("SUCCESS");
        serviceStatus.setMessage("Content Updated Successfully");
        UpdateServiceResponse response = new UpdateServiceResponse();
        response.setServiceStatus(serviceStatus);
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteServiceRequest")
    @ResponsePayload
    public DeleteServiceResponse deleteService(@RequestPayload DeleteServiceRequest request) {
        Service service = soapService.getServiceById(request.getId());
        ServiceStatus serviceStatus = new ServiceStatus();
        if (service == null ) {
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Content Not Available");
        } else {
            soapService.deleteService(service);
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Deleted Successfully");
        }
        DeleteServiceResponse response = new DeleteServiceResponse();
        response.setServiceStatus(serviceStatus);
        return response;
    }
}
