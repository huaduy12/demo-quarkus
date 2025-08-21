package com.example.config.security;

import com.example.cim.model.UserDto;
import com.example.config.filter.UserFilter;
import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import java.util.List;


@CheckPermission
@Priority(Interceptor.Priority.APPLICATION)
@Interceptor
public class PermissionInterceptor {

    @AroundInvoke
    Object checkPermission(InvocationContext ctx) throws Exception {
        CheckPermission annotation = ctx.getMethod().getAnnotation(CheckPermission.class);
        if (annotation == null) {
            annotation = ctx.getTarget().getClass().getAnnotation(CheckPermission.class);
        }

        if (annotation != null) {
            String required = annotation.value();

            UserDto userDto = UserFilter.getCurrentUser();
            List<String> permissions = List.of("VIEW", "EDIT");
            if (userDto == null || !permissions.contains(required)) {
                throw new SecurityException("Access denied, missing permission: " + required);
            }
        }

        return ctx.proceed();
    }
}
