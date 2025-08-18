package com.example.cim.rabbimq;

import com.example.cim.model.CategoryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

@ApplicationScoped
public class CategoryConsumer {

    @Inject
    ObjectMapper mapper;

    @Incoming("category-in")
    public void consume(String msg) {
        try {
            CategoryDto categoryDto = mapper.readValue(msg, CategoryDto.class);
            System.out.println("📦 Received category: " + categoryDto.toString());
        } catch (Exception e) {
            System.out.println("Error queue");
        }
    }
}
