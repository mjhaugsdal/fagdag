package org.acme;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.openapi.OpenApiFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class SpringApplication {

    @Autowired
    private Bus bus;

    @Autowired
    private FF4jConfig config;

    @Value("${cxf-server.port}")
    String port;

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
    }

    @Bean
    public Server rsServer() {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setBus(bus);
        endpoint.setAddress("http://localhost:" + port);
        //endpoint.setServiceBean(new SoknadService());
        endpoint.setServiceBean(new SoknadService(config.getFF4j()));

        List<Feature> features = new LinkedList<>();
        features.add(new OpenApiFeature());

        var provider = new JacksonJsonProvider();
        List<Object> providers = new LinkedList<>();
        providers.add(provider);

        endpoint.setProviders(providers);
        endpoint.setFeatures(features);

        return endpoint.create();
    }
}
