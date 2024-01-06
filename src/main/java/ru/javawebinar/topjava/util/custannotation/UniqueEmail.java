package ru.javawebinar.topjava.util.custannotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UniqueEmail {
    String message() default "Invalid email value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
