package ru.javawebinar.topjava.service.jpa;

import org.junit.BeforeClass;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

@ActiveProfiles(profiles = {Profiles.JPA})
public class JpaUserServiceTest extends AbstractUserServiceTest {
    @BeforeClass
    public static void clearResult() {
        results.setLength(0);
    }
}
