package com.example.cim.rabbimq;

import com.example.cim.model.CategoryDto;
import com.example.config.enums.PublicError;
import com.example.config.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;


@ApplicationScoped
public class CategoryProducer {

    private static final Logger LOG = Logger.getLogger(CategoryProducer.class);

    @Inject
    @Channel("category-out")
    Emitter<String> emitter;

    @Inject
    ObjectMapper mapper;

    public void send(CategoryDto categoryDto) {
        try {
            String json = mapper.writeValueAsString(categoryDto);
            LOG.infof("Sending category ID=%d to RabbitMQ: %s", categoryDto.getId(), json);
            emitter.send(json);
            LOG.infof("Successfully sent category ID=%d", categoryDto.getId());
        } catch (Exception e) {
            LOG.errorf("Failed to send category ID=%d: %s", categoryDto.getId(), e.getMessage());
            throw new BusinessException(PublicError.BAD_REQUEST_DATA, Response.Status.BAD_REQUEST,e.getMessage());
        }
    }
}
