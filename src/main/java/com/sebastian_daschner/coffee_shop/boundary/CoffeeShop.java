package com.sebastian_daschner.coffee_shop.boundary;

import com.sebastian_daschner.coffee_shop.entity.Order;
import com.sebastian_daschner.coffee_shop.entity.OrderStatus;

import javax.ejb.Stateless;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

@Stateless
public class CoffeeShop {

    public List<String> getCoffeeTypes() {
        return asList("Espresso", "Pour-over");
    }

    public List<String> getOrigins(final String type) {
        return asList("Colombia", "El Salvador");
    }

    public void createOrder(final Order order) {
        order.setId(UUID.randomUUID().toString());
        System.out.println("order = " + order);
    }

    public Order getOrder(final String id) {
        return new Order(id, "Espresso", "Colombia", OrderStatus.FINISHED);
    }

    public void updateOrder(final String id, final Order order) {
        System.out.println("updated: " + order + " with id: " + id);
    }
}
