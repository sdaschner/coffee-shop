package com.sebastian_daschner.coffee_shop.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String id;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private CoffeeType type;

    @ManyToOne(optional = false)
    private Origin origin;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PREPARING;

    public Order() {
    }

    public Order(UUID id, CoffeeType type, Origin origin) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(type);
        Objects.requireNonNull(origin);
        this.id = id.toString();
        this.type = type;
        this.origin = origin;
    }

    public String getId() {
        return id;
    }

    public CoffeeType getType() {
        return type;
    }

    public Origin getOrigin() {
        return origin;
    }

    public OrderStatus getStatus() {
        return status;
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
