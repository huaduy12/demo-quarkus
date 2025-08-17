package com.example.controller;

import com.example.model.BaseResponse;
import com.example.model.CurrencyDto;
import com.example.service.CurrencyService;
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

@RequiredArgsConstructor
@Path("/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.status(Response.Status.OK)
                .entity(BaseResponse.success(currencyService.list()))
                .build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam(value = "pageSize") Integer pageSize, @QueryParam("pageNum") Integer pageNum, CurrencyDto currencyDto) {
        return Response.status(Response.Status.OK)
                .entity(BaseResponse.success(currencyService.search2(pageSize, pageNum, currencyDto)))
                .build();
    }

    @GET
    @Path("/{ccyCode}")
    public BaseResponse<CurrencyDto> get(@PathParam("ccyCode") String ccyCode) {
        return BaseResponse.success(currencyService.detail(ccyCode));
    }

    @POST
    public Response create(@Valid CurrencyDto currencyDto) {
        return Response.ok(BaseResponse.success(currencyService.add(currencyDto))).build();
    }

    @PUT
    public BaseResponse<String> update(CurrencyDto currencyDto) {
        return BaseResponse.success(currencyService.update(currencyDto));
    }

    @DELETE
    @Path("/{ccyCode}")
    public BaseResponse<String> delete(@PathParam("ccyCode") String ccyCode) {
        currencyService.delete(ccyCode);
        return BaseResponse.success(null);
    }
}
