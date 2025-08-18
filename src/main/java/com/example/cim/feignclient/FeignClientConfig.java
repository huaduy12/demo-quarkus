package com.example.cim.feignclient;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

import java.util.UUID;

public class FeignClientConfig implements ClientHeadersFactory {
    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders, MultivaluedMap<String, String> clientOutgoingHeaders) {
        MultivaluedMap<String, String> result = new MultivaluedHashMap<>();

        // Propagate Authorization nếu có
//        String auth = incomingHeaders.getFirst("Authorization");
//        if (auth != null) {
//            result.add("Authorization", auth);
//        }
        // Thêm custom header
        result.add("X-Request-Source", "quarkus-app");
        result.add("X-Trace-Id", UUID.randomUUID().toString());

        // Giữ lại các header sẵn
        result.putAll(incomingHeaders);
        result.putAll(clientOutgoingHeaders);
        return result;
    }
}
