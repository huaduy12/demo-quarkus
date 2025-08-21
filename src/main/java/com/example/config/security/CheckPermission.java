package com.example.config.security;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.enterprise.util.Nonbinding;
import jakarta.interceptor.InterceptorBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@InterceptorBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface CheckPermission {
    @Nonbinding
    String value() default "";
}
