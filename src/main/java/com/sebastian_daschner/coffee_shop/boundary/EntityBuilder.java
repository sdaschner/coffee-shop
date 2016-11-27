package com.sebastian_daschner.coffee_shop.boundary;

import com.sebastian_daschner.coffee_shop.entity.Order;
import com.sebastian_daschner.coffee_shop.entity.OrderStatus;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class EntityBuilder {

    public JsonObject buildOrder(final Order order, final UriInfo uriInfo) {
        final URI orderUri = uriInfo.getBaseUriBuilder().path(OrdersResource.class).path(OrdersResource.class, "getOrder").build(order.getId());
        final JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("type", order.getType())
                .add("origin", order.getOrigin())
                .add("status", order.getStatus().name().toLowerCase());

        if (order.getStatus() != OrderStatus.COLLECTED)
            builder.add("_actions", Json.createObjectBuilder()
                    .add("collect-coffee", Json.createObjectBuilder()
                            .add("method", "PUT")
                            .add("href", orderUri.toString())
                            .add("fields", Json.createArrayBuilder()
                                    .add(Json.createObjectBuilder()
                                            .add("name", "status").add("value", "collected")
                                    ).add(Json.createObjectBuilder()
                                            .add("name", "type").add("value", order.getType()))
                                    .add(Json.createObjectBuilder()
                                            .add("name", "origin").add("value", order.getOrigin()))
                            )));

        return builder.build();
    }

    public JsonObject buildIndex(final UriInfo uriInfo) {
        final URI typesUri = uriInfo.getBaseUriBuilder().path(TypesResource.class).build();
        final URI ordersUri = uriInfo.getBaseUriBuilder().path(OrdersResource.class).build();
        return Json.createObjectBuilder()
                .add("_links", Json.createObjectBuilder()
                        .add("types", typesUri.toString())
                        .add("orders", ordersUri.toString()))
                .add("_actions", Json.createObjectBuilder()
                        .add("order-coffee", Json.createObjectBuilder()
                                .add("method", "POST")
                                .add("href", ordersUri.toString())
                                .add("fields", Json.createArrayBuilder()
                                        .add(Json.createObjectBuilder()
                                                .add("name", "type")
                                                .add("type", "text"))
                                        .add(Json.createObjectBuilder()
                                                .add("name", "origin")
                                                .add("type", "text"))
                                )))
                .build();
    }

    public JsonObject buildOrigin(final UriInfo uriInfo, final String origin, final String type) {
        final URI ordersUri = uriInfo.getBaseUriBuilder().path(OrdersResource.class).build();
        return Json.createObjectBuilder()
                .add("origin", origin)
                .add("_actions", Json.createObjectBuilder()
                        .add("order-coffee", Json.createObjectBuilder()
                                .add("method", "POST")
                                .add("href", ordersUri.toString())
                                .add("fields", Json.createArrayBuilder()
                                        .add(Json.createObjectBuilder()
                                                .add("name", "type")
                                                .add("value", capitalize(type.toLowerCase())))
                                        .add(Json.createObjectBuilder()
                                                .add("name", "origin")
                                                .add("type", origin))
                                )))
                .build();
    }

    public JsonObjectBuilder buildType(final String type, final UriInfo uriInfo) {
        return Json.createObjectBuilder()
                .add("type", type)
                .add("_links", Json.createObjectBuilder()
                        .add("origins", uriInfo.getBaseUriBuilder()
                                .path(TypesResource.class)
                                .path(TypesResource.class, "originsResource")
                                .build(type).toString().toLowerCase()));
    }

    private String capitalize(final String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

}
