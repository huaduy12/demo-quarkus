package com.example.exception;

import com.example.enums.PublicError;
import com.example.model.BaseResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.stream.Collectors;

@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getMessage())
                .collect(Collectors.joining("; "));

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(BaseResponse.errorData(PublicError.BAD_REQUEST_DATA, message))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
