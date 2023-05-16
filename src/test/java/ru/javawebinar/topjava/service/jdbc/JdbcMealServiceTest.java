package ru.javawebinar.topjava.service.jdbc;

import org.junit.BeforeClass;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

@ActiveProfiles(profiles = {Profiles.JDBC})
public class JdbcMealServiceTest extends AbstractMealServiceTest {
    @BeforeClass
    public static void clearResult() {
        results.setLength(0);
    }
}
