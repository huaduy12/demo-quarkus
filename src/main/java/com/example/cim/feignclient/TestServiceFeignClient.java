package com.example.cim.feignclient;

import com.example.cim.model.UserDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@ApplicationScoped
@RegisterRestClient(configKey = "test-service")
@RegisterClientHeaders(FeignClientConfig.class)
public interface TestServiceFeignClient {

    @POST
    @Path("/test1")
    Response searchUser(UserDto userDto);
}
