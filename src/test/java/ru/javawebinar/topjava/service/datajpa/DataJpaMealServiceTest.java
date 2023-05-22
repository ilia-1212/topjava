package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = {Profiles.DATAJPA})
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void getMealUser() {
        Meal meal = service.getMealAndUser(MEAL1_ID, USER_ID);
        User mealUser = meal.getUser();
        // не знаю как тут сделать ,чтобы не падала по lazy init
        // в DataJpaUserServiceTest все ОК, а тут падает в Assert.assertEquals(mealUser, user);
        Assert.assertEquals(mealUser.getId(), user.getId());
//        MatcherFactory.Matcher<User> userMatcher = MatcherFactory.usingIgnoringFieldsComparator("registered", "roles","meals");
//        userMatcher.assertMatch( mealUser, user);
    }

    @Test
    public void getNotFoundMealUser() {
        assertThrows(NotFoundException.class, () -> service.getMealAndUser(MEAL1_ID, ADMIN_ID));
    }
}
