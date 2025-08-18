package com.example.config.exception;

import com.example.config.enums.PublicError;
import com.example.notify.model.BaseResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Provider
@Slf4j
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        log.error(exception.getMessage(), exception);
        return switch (exception) {
            case BusinessException ex ->
                    buildError(ex.getStatus(), BaseResponse.errorData(ex.getPublicError(), ex.getData()));

            case SecurityException ex -> buildError(Response.Status.FORBIDDEN,
                    BaseResponse.errorData(PublicError.FORBIDDEN, "Access denied"));

            case SQLException ex -> buildError(Response.Status.INTERNAL_SERVER_ERROR,
                    BaseResponse.errorData(PublicError.DATA_ACCESS_ERROR, "Database error occurred"));

            default -> buildError(Response.Status.INTERNAL_SERVER_ERROR,
                    BaseResponse.errorData(PublicError.UNEXPECTED_ERROR, "Internal server error"));
        };
    }

    private Response buildError(Response.Status status, BaseResponse<?> body) {
        return Response.status(status)
                .entity(body)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
