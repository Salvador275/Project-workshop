package com.crusher.work.endpoints;

import com.crusher.work.entities.Application;
import com.crusher.work.entities.Client;
import com.crusher.work.entities.Service;
import com.crusher.work.service.IService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap_service.applications.*;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class ApplicationEndpoint {
    private static final String NAMESPACE_URI = "http://soap-service/applications";
    @Autowired
    private IService soapService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getServiceByIdRequest")
    @ResponsePayload
    public GetApplicationByIdResponse getService(@RequestPayload GetApplicationByIdRequest request) {
        GetApplicationByIdResponse response = new GetApplicationByIdResponse();
        ApplicationInfo applicationInfo = new ApplicationInfo();
        BeanUtils.copyProperties(soapService.getServiceById(request.getId()), applicationInfo);
        response.setApplication(applicationInfo);
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllApplicationsRequest")
    @ResponsePayload
    public GetAllApplicationsResponse getAllApplications() {
        GetAllApplicationsResponse response = new GetAllApplicationsResponse();
        List<ApplicationInfo> serviceInfoList = new ArrayList<>();
        List<Application> serviceList = soapService.getAllApplications();
        for (int i = 0; i < serviceList.size(); i++) {
            ApplicationInfo ob = new ApplicationInfo();
            BeanUtils.copyProperties(serviceList.get(i), ob);
            serviceInfoList.add(ob);
        }
        response.getApplication().addAll(serviceInfoList);
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addServiceRequest")
    @ResponsePayload
    public AddApplicationResponse addService(@RequestPayload AddApplicationRequest request) {
        AddApplicationResponse response = new AddApplicationResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        Application application = new Application();
        Client client = soapService.getClientById(request.getClientId());
        Service service = soapService.getServiceById(request.getServiceId());
        application.setClient(client);
        application.setService(service);
        boolean flag = soapService.addApplication(application);
        if (flag == false) {
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Content Already Available");
            response.setServiceStatus(serviceStatus);
        } else {
            ApplicationInfo applicationInfo = new ApplicationInfo();
            BeanUtils.copyProperties(application, applicationInfo);
            response.setApplication(applicationInfo);
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Added Successfully");
            response.setServiceStatus(serviceStatus);
        }
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateServiceRequest")
    @ResponsePayload
    public UpdateApplicationResponse updateApplication(@RequestPayload UpdateApplicationRequest request) {
        Application application = new Application();
        BeanUtils.copyProperties(request.getApplication(), application);
        soapService.updateApplication(application);
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode("SUCCESS");
        serviceStatus.setMessage("Content Updated Successfully");
        UpdateApplicationResponse response = new UpdateApplicationResponse();
        response.setServiceStatus(serviceStatus);
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteServiceRequest")
    @ResponsePayload
    public DeleteApplicationResponse deleteService(@RequestPayload DeleteApplicationRequest request) {
        Application application = soapService.getApplicationById(request.getId());
        ServiceStatus serviceStatus = new ServiceStatus();
        if (application == null ) {
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Content Not Available");
        } else {
            soapService.deleteApplication(application);
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Deleted Successfully");
        }
        DeleteApplicationResponse response = new DeleteApplicationResponse();
        response.setServiceStatus(serviceStatus);
        return response;
    }
}
