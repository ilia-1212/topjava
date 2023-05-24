package ru.javawebinar.topjava.service.datajpa;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = {Profiles.DATAJPA})
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @BeforeClass
    public static void clearResult() {

        try {
            Class.forName("ru.javawebinar.topjava.UserTestData");
            Class.forName("ru.javawebinar.topjava.MealTestData");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getWithMeals() {
        User userActual = service.getWithMeals(USER_ID);
        User userExpect = getWithMeal();
        USER_MATCHER.assertMatch(userActual, userExpect);
        MEAL_MATCHER.assertMatch(userActual.getMeals(), userExpect.getMeals());
    }

    @Test
    public void getWithNoMeals() {
        User userMealsActual = service.getWithMeals(GUEST_ID);
        USER_MATCHER.assertMatch(userMealsActual, guest);
        MEAL_MATCHER.assertMatch(userMealsActual.getMeals(), guest.getMeals());
    }

    @Test
    public void getNotFoundWithMeals() {
        assertThrows(NotFoundException.class, () -> service.getWithMeals(NOT_FOUND));
    }
}
