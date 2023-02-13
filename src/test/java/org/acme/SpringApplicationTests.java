package org.acme;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.acme.model.Soknad;
import org.acme.model.SoknadSvar;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class SpringApplicationTests {

	static String address = "https://localhost:8080/soknad/";
	static WebClient client;

	@BeforeAll
	static void contextLoads() {
		SpringApplication.main(null);
		List<Object> providers = new ArrayList<Object>();
		providers.add(new JacksonJsonProvider());
		client = WebClient.create(address, providers, "./client.xml");
		client.encoding("UTF-8");
		client.type(MediaType.APPLICATION_JSON);
	}

	@Test
	void testPostAndGetSoknad() throws FileNotFoundException {
		var filename = "src/test/resources/testsoknad.json";
		JsonReader reader = new JsonReader(new FileReader(filename));
		Gson json = new Gson();
		var data = (Soknad) json.fromJson(reader, Soknad.class);

		var response = client.post(data);
		var responseEntity = response.readEntity(SoknadSvar.class);

		client.resetQuery().query("id", responseEntity.getId());
		var soknadSvar = client.get(SoknadSvar.class);

		Assertions.assertTrue(soknadSvar.getStatus().equalsIgnoreCase("Mottatt"));
	}

	@Test
	void testPostSoknad() throws IOException {
		var filename = "src/test/resources/testsoknad.json";
		JsonReader reader = new JsonReader(new FileReader(filename));
		Gson json = new Gson();
		var data = json.fromJson(reader, Soknad.class);

		var response = client.post(data);
		var responseEntity = response.readEntity(SoknadSvar.class);
		Assertions.assertTrue(responseEntity.getStatus().equalsIgnoreCase("Mottatt"));

	}

	@Test
	void testGetSoknadUkjent() throws IOException {
		client.resetQuery().query("id", Integer.MAX_VALUE);
		var soknadSvar = client.get(SoknadSvar.class);
		Assertions.assertTrue(soknadSvar.getStatus().equalsIgnoreCase("Ukjent"));
	}
}
