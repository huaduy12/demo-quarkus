package com.example.cim.rabbimq;

import com.example.cim.model.CategoryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
@Slf4j
public class CategoryConsumer {

    @Inject
    ObjectMapper mapper;

    @Incoming("category-in")
    public void consume(String msg) {
        try {
            CategoryDto categoryDto = mapper.readValue(msg, CategoryDto.class);
            log.info("Received category: " + categoryDto.toString());
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
        }
    }
}
