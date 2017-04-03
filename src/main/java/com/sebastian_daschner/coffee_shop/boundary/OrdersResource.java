package com.sebastian_daschner.coffee_shop.boundary;

import com.sebastian_daschner.coffee_shop.control.EntityBuilder;
import com.sebastian_daschner.coffee_shop.entity.Order;
import com.sebastian_daschner.coffee_shop.entity.ValidOrder;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.UUID;

@Path("orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrdersResource {

    @Inject
    CoffeeShop coffeeShop;

    @Inject
    EntityBuilder entityBuilder;

    @Context
    UriInfo uriInfo;

    @GET
    @Path("{id}")
    public JsonObject getOrder(@PathParam("id") UUID id) {
        final Order order = coffeeShop.getOrder(id);

        if (order == null)
            throw new NotFoundException();

        return entityBuilder.buildOrder(order);
    }

    @POST
    public Response createOrder(@Valid @ValidOrder JsonObject json) {
        final Order order = entityBuilder.buildOrder(json);

        coffeeShop.createOrder(order);

        return Response.created(buildUri(order)).build();
    }

    private URI buildUri(Order order) {
        return uriInfo.getBaseUriBuilder()
                .path(OrdersResource.class)
                .path(OrdersResource.class, "getOrder")
                .build(order.getId());
    }

}
