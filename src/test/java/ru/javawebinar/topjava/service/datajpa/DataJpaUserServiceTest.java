package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = {Profiles.DATAJPA})
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void getUserAndHisMeal() {
        User UserMeals = service.getUserAndHisMeal(USER_ID);
        Assert.assertEquals(UserMeals, user);
    }

    @Test
    public void getNotFoundUserAndHisMeal() {
        assertThrows(NotFoundException.class, () -> service.getUserAndHisMeal(NOT_FOUND));
    }
}
