package com.example.config.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class MethodFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String method = requestContext.getMethod();
        if (!method.equals("GET") && !method.equals("POST")
                && !method.equals("PUT") && !method.equals("DELETE")) {
            requestContext.abortWith(
                    jakarta.ws.rs.core.Response.status(405)
                            .entity("Method not allowed: " + method)
                            .build()
            );
        }
    }
}