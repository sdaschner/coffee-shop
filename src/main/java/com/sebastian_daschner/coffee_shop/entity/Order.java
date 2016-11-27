package com.sebastian_daschner.coffee_shop.entity;

import javax.validation.constraints.NotNull;

public class Order {

    private String id;
    @NotNull
    private String type;
    @NotNull
    private String origin;
    private OrderStatus status;

    public Order() {
    }

    public Order(final String id, final String type, final String origin, final OrderStatus status) {
        this.id = id;
        this.type = type;
        this.origin = origin;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(final String origin) {
        this.origin = origin;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(final OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", origin='" + origin + '\'' +
                ", status=" + status +
                '}';
    }

}
