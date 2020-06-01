package com.crusher.work.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
public class WSConfig extends WsConfigurerAdapter {
    //clients @Bean
    @Bean
    public ServletRegistrationBean messageDispatcherServletClient(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/clients-soap/*");
    }
    @Bean(name = "clients")
    public DefaultWsdl11Definition defaultWsdl11DefinitionClient(XsdSchema clientsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("ClientsPort");
        wsdl11Definition.setLocationUri("/clients-soap");
        wsdl11Definition.setTargetNamespace("http://soap-service/clients");
        wsdl11Definition.setSchema(clientsSchema);
        return wsdl11Definition;
    }
    @Bean
    public XsdSchema clientsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsds/client.xsd"));
    }

    //services @Bean
    @Bean
    public ServletRegistrationBean messageDispatcherServletServices(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/services-soap/*");
    }
    @Bean(name = "services")
    public DefaultWsdl11Definition defaultWsdl11DefinitionService(XsdSchema servicesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("ClientsPort");
        wsdl11Definition.setLocationUri("/clients-soap");
        wsdl11Definition.setTargetNamespace("http://soap-service/services");
        wsdl11Definition.setSchema(servicesSchema);
        return wsdl11Definition;
    }
    @Bean
    public XsdSchema servicesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsds/serviceEntity.xsd"));
    }

    //application @Bean
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/clients-soap/*");
    }
    @Bean(name = "applications")
    public DefaultWsdl11Definition defaultWsdl11DefinitionApplication(XsdSchema applicationsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("ApplicationsPort");
        wsdl11Definition.setLocationUri("/applications-soap");
        wsdl11Definition.setTargetNamespace("http://soap-service/applications");
        wsdl11Definition.setSchema(applicationsSchema);
        return wsdl11Definition;
    }
    @Bean
    public XsdSchema applicationsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsds/application.xsd"));
    }
}
