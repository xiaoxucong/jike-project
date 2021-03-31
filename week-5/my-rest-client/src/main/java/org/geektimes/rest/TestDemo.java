package org.geektimes.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestDemo {
    public static void main(String[] args) {
        Map<String, Object> param =
                new HashMap<>();
        param.put("name",  "2018112415380");
        MediaType mediaType = new MediaType(   "application","json","utf-8");
        Entity entity =Entity.entity(param, mediaType);
        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://127.0.0.1:8080/hello/world")      // WebTarget
                .request() // Invocation.Builder
                .post(entity);                                     //  Response

        String content = response.readEntity(String.class);

        System.out.println(content);

    }
}
