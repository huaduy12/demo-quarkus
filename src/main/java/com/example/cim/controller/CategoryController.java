package com.example.cim.controller;

import com.example.cim.feignclient.TestServiceFeignClient;
import com.example.cim.model.BaseResponse;
import com.example.cim.model.CategoryDto;
import com.example.cim.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@RequiredArgsConstructor
@Path("/category")
@Tag(name = "Category", description = "Category management endpoints")
public class CategoryController {

    private final CategoryService categoryService;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Successfully retrieved currency")
    public Response list() {
        return Response.status(Response.Status.OK)
                .entity(BaseResponse.success(categoryService.list()))
                .build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam(value = "pageSize") Integer pageSize, @QueryParam("pageNum") Integer pageNum, CategoryDto categoryDto) {
        return Response.status(Response.Status.OK)
                .entity(BaseResponse.success(categoryService.search(pageSize, pageNum, categoryDto)))
                .build();
    }

    @GET
    @Path("/{code}")
    public BaseResponse<CategoryDto> get(@PathParam("code") String code) {
        return BaseResponse.success(categoryService.detail(code));
    }

    @POST
    public Response create(@Valid CategoryDto categoryDto) {
        return Response.ok(BaseResponse.success(categoryService.add(categoryDto))).build();
    }

    @PUT
    public BaseResponse<String> update(CategoryDto categoryDto) {
        return BaseResponse.success(categoryService.update(categoryDto));
    }

    @DELETE
    @Path("/{code}")
    public BaseResponse<String> delete(@PathParam("code") String code) {
        categoryService.delete(code);
        return BaseResponse.success(null);
    }
}
