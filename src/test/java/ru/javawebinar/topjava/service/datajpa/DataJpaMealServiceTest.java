package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.NOT_FOUND;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = {Profiles.DATAJPA})
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void getWithUser() {
        Meal mealActual = service.getWithUser(MEAL1_ID, USER_ID);
        MEAL_MATCHER.assertMatch(mealActual, meal1);
        USER_MATCHER.assertMatch(mealActual.getUser(), user);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.getWithUser(NOT_FOUND, ADMIN_ID));
    }

    @Test
    public void getNotFoundWithUser() {
        assertThrows(NotFoundException.class, () -> service.getWithUser(MEAL1_ID, ADMIN_ID));
    }
}
