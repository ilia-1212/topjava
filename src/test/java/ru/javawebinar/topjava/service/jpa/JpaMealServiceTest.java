package ru.javawebinar.topjava.service.jpa;

import org.junit.BeforeClass;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

@ActiveProfiles(profiles = {Profiles.JPA})
public class JpaMealServiceTest extends AbstractMealServiceTest {
    @BeforeClass
    public static void clearResult() {
        results.setLength(0);
    }
}
