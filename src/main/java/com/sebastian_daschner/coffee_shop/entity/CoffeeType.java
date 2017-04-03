package com.sebastian_daschner.coffee_shop.entity;

import java.util.stream.Stream;

public enum CoffeeType {

    ESPRESSO,
    POUR_OVER,
    LATTE;

    public static CoffeeType fromString(String string) {
        return Stream.of(CoffeeType.values())
                .filter(t -> t.name().equalsIgnoreCase(string))
                .findAny().orElse(null);
    }

}
