package com.example.config.exception;

import com.example.config.enums.PublicError;
import jakarta.ws.rs.core.Response;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class BusinessException extends RuntimeException {
    private final PublicError publicError;
    private final Response.Status status;
    private final Serializable data;

    public BusinessException(PublicError publicError) {
        super(publicError.getDesc());
        this.publicError = publicError;
        this.status = Response.Status.BAD_REQUEST;
        this.data = null;
    }

    public BusinessException(PublicError publicError, Response.Status status, Serializable data) {
        super(publicError.getDesc());
        this.publicError = publicError;
        this.status = status;
        this.data = data;
    }
}
