package com.sebastian_daschner.coffee_shop.control;

import com.sebastian_daschner.coffee_shop.entity.Created;

import javax.json.JsonObject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreatedOrderValidator implements ConstraintValidator<Created, JsonObject> {

    public void initialize(Created constraint) {
    }

    public boolean isValid(JsonObject json, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        final String type = json.getString("type", null);
        final String origin = json.getString("origin", null);

        boolean valid = true;

        if (type == null) {
            context.buildConstraintViolationWithTemplate("{javax.validation.constraints.NotNull.message}")
                    .addPropertyNode("type").addConstraintViolation();
            valid = false;
        }

        if (origin == null) {
            context.buildConstraintViolationWithTemplate("{javax.validation.constraints.NotNull.message}")
                    .addPropertyNode("origin").addConstraintViolation();
            valid = false;
        }

        return valid;
    }

}
