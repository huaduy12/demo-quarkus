package com.example.cim.controller;

import com.example.cim.model.BaseResponse;
import com.example.cim.model.CategoryDto;
import com.example.cim.model.UserDto;
import com.example.cim.redis.CategoryCacheManual;
import com.example.config.filter.UserFilter;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@RequiredArgsConstructor
@Path("/cache")
@Tag(name = "Cache", description = "Cache management endpoints")
public class CacheManualController {

    private final CategoryCacheManual categoryCacheManual;


    @GET
    @Path("/{code}")
    public Response get(@PathParam("code") String code) {
        return Response.status(Response.Status.OK)
                .entity(BaseResponse.success(categoryCacheManual.getCategoryCache(code)))
                .build();
    }


    @POST
    public Response create(@Valid CategoryDto categoryDto) {
        UserDto userDto = UserFilter.getCurrentUser();
        System.out.println("USER: " + userDto);
        categoryCacheManual.setCategoryCache(categoryDto.getCode(), categoryDto, 3600);
        return Response.status(Response.Status.OK)
                .entity(BaseResponse.success("Thành công"))
                .build();
    }

    @PUT
    public Response update(CategoryDto categoryDto) {
        categoryCacheManual.setCategoryCache(categoryDto.getCode(), categoryDto, 3600);
        return Response.status(Response.Status.OK)
                .entity(BaseResponse.success("Thành công"))
                .build();
    }

    @DELETE
    @Path("/{code}")
    public Response delete(@PathParam("code") String code) {
        categoryCacheManual.clearCategoryCache(code);
        return Response.status(Response.Status.OK)
                .entity(BaseResponse.success("Thành công"))
                .build();
    }
}
