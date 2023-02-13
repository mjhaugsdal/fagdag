package org.acme;

import org.apache.cxf.bus.spring.SpringBusFactory;

public class SpringApplication {

	public static void main(String[] args) {
		new SpringBusFactory().createBus("cxf-service.xml");
	}
}
