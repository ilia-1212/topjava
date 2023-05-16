package ru.javawebinar.topjava.service.jdbc;

import org.junit.BeforeClass;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

@ActiveProfiles(profiles = {Profiles.JDBC})
public class JdbcUserServiceTest extends AbstractUserServiceTest {
    @BeforeClass
    public static void clearResult() {
        results.setLength(0);
    }
}
