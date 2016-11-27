package com.sebastian_daschner.coffee_shop.control;

import com.sebastian_daschner.coffee_shop.entity.OrderStatus;
import com.sebastian_daschner.coffee_shop.entity.Updated;

import javax.json.JsonObject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UpdatedOrderValidator implements ConstraintValidator<Updated, JsonObject> {
    public void initialize(Updated constraint) {
    }

    public boolean isValid(JsonObject jsonObject, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        final String status = jsonObject.getString("status", null);
        if (!OrderStatus.COLLECTED.name().toLowerCase().equals(status)) {
            context.buildConstraintViolationWithTemplate("must be changed to collected")
                    .addPropertyNode("status").addConstraintViolation();
            return false;
        }
        return true;
    }

}
