package com.example.cim.rabbimq;

import com.example.cim.model.CategoryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class CategoryProducer {

    @Channel("category-out")
    Emitter<String> emitter;

    @Inject
    ObjectMapper mapper;

    public void send(CategoryDto categoryDto) {
        try {
            String json = mapper.writeValueAsString(categoryDto);
            emitter.send(json);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize order", e);
        }
    }
}
