package com.sebastian_daschner.coffee_shop.boundary;

import com.sebastian_daschner.coffee_shop.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.entity.Order;
import com.sebastian_daschner.coffee_shop.entity.Origin;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
public class CoffeeShop {

    @PersistenceContext
    EntityManager entityManager;

    public Set<CoffeeType> getCoffeeTypes() {
        return EnumSet.of(CoffeeType.ESPRESSO, CoffeeType.LATTE, CoffeeType.POUR_OVER);
    }

    public Origin getOrigin(String name) {
        return entityManager.find(Origin.class, name);
    }

    public Order getOrder(UUID id) {
        return entityManager.find(Order.class, id.toString());
    }

    public Set<Origin> getOrigins(final CoffeeType type) {
        return entityManager.createNamedQuery(Origin.FIND_ALL, Origin.class)
                .getResultList().stream()
                .filter(t -> t.getCoffeeTypes().contains(type))
                .collect(Collectors.toSet());
    }

    public void createOrder(Order order) {
        entityManager.merge(order);
        entityManager.flush();
    }

}
