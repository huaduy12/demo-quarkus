package com.example.config.filter;

import com.example.cim.model.UserDto;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class UserFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final ThreadLocal<UserDto> currentUser = new ThreadLocal<>();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Lấy token từ header
        String token = requestContext.getHeaderString("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            requestContext.abortWith(
                    jakarta.ws.rs.core.Response.status(401).entity("Missing or invalid token").build()
            );
            return;
        }
        token = token.substring("Bearer ".length());
        // TODO: parse token để lấy user, ví dụ trước:
        UserDto user = new UserDto();
        user.setName("test");
        user.setEmail("test@example.com");

        // Lưu vào ThreadLocal
        currentUser.set(user);
    }

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
        // Sau khi xử lý xong thì clear context
        currentUser.remove();
    }

    public static UserDto getCurrentUser() {
        return currentUser.get();
    }
}
