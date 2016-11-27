package com.sebastian_daschner.coffee_shop.boundary;

import com.sebastian_daschner.coffee_shop.entity.Created;
import com.sebastian_daschner.coffee_shop.entity.Order;
import com.sebastian_daschner.coffee_shop.entity.OrderStatus;
import com.sebastian_daschner.coffee_shop.entity.Updated;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.stream.Stream;

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

    @POST
    public Response createOrder(@Valid @Created JsonObject json) {
        final Order order = createOrderFromJson(json);
        coffeeShop.createOrder(order);
        final URI orderUri = uriInfo.getBaseUriBuilder().path(OrdersResource.class).path(OrdersResource.class, "getOrder").build(order.getId());
        return Response.created(orderUri).build();
    }

    @GET
    @Path("{id}")
    public JsonObject getOrder(@PathParam("id") String id) {
        final Order order = coffeeShop.getOrder(id);
        return entityBuilder.buildOrder(order, uriInfo);
    }

    @PUT
    @Path("{id}")
    public void updateOrder(@PathParam("id") String id, @Valid @Updated JsonObject json) {
        final Order order = createOrderFromJson(json);
        coffeeShop.updateOrder(id, order);
    }

    private Order createOrderFromJson(final JsonObject json) {
        final String type = json.getString("type");
        final String origin = json.getString("origin");
        final String statusString = json.getString("status", null);

        final OrderStatus status = Stream.of(OrderStatus.values())
                .filter(s -> s.name().equalsIgnoreCase(statusString))
                .findAny().orElse(OrderStatus.PREPARING);

        return new Order(null, type, origin, status);
    }

}
