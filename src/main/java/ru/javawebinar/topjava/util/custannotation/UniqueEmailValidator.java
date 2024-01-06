package ru.javawebinar.topjava.util.custannotation;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;

    public UniqueEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UniqueEmail email) {
        ConstraintValidator.super.initialize(email);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid  = value != null && value.contains(".space");
            User user = userRepository.getByEmail(value);
            return isValid && user == null;
    }
}
