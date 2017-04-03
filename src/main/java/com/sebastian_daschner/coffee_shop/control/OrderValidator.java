package com.sebastian_daschner.coffee_shop.control;

import com.sebastian_daschner.coffee_shop.boundary.CoffeeShop;
import com.sebastian_daschner.coffee_shop.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.entity.Origin;
import com.sebastian_daschner.coffee_shop.entity.ValidOrder;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OrderValidator implements ConstraintValidator<ValidOrder, JsonObject> {

    @Inject
    CoffeeShop coffeeShop;

    public void initialize(ValidOrder constraint) {
        // nothing to do
    }

    public boolean isValid(JsonObject json, ConstraintValidatorContext context) {
        final String type = json.getString("type", null);
        final String origin = json.getString("origin", null);

        final CoffeeType coffeeType = coffeeShop.getCoffeeTypes().stream()
                .filter(t -> t.name().equalsIgnoreCase(type))
                .findAny().orElse(null);

        final Origin coffeeOrigin = coffeeShop.getOrigin(origin);

        if (coffeeOrigin == null || coffeeType == null)
            return false;

        return coffeeOrigin.getCoffeeTypes().contains(coffeeType);
    }

}
