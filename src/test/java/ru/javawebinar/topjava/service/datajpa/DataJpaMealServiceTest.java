package ru.javawebinar.topjava.service.datajpa;

import org.junit.BeforeClass;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

@ActiveProfiles(profiles = {Profiles.DATAJPA})
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @BeforeClass
    public static void clearResult() {
        results.setLength(0);
    }
}
