package com.example.config.exception;

import com.example.config.enums.PublicError;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusinessException extends RuntimeException{
    private PublicError publicError;
    private Response.Status status = Response.Status.BAD_REQUEST;
    private Object data;

    public BusinessException(PublicError publicError) {
        super(publicError.getDesc());
        this.publicError = publicError;
    }

    public BusinessException( PublicError publicError, Response.Status status, Object data) {
        this.publicError = publicError;
        this.status = status;
        this.data = data;
    }
}
