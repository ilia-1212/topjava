package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles(profiles = {Profiles.JDBC})
public class UserServiceJdbcTest extends UserServiceBaseTest {
    @Test
    @Override
    public void create() {
        super.create();
    }

    @Test
    @Override
    public void duplicateMailCreate() {
        super.duplicateMailCreate();
    }

    @Test
    @Override
    public void delete() {
        super.delete();
    }

    @Test
    @Override
    public void deletedNotFound() {
        super.deletedNotFound();
    }

    @Test
    @Override
    public void get() {
        super.get();
    }

    @Test
    @Override
    public void getNotFound() {
        super.getNotFound();
    }

    @Test
    @Override
    public void getByEmail() {
        super.getByEmail();
    }

    @Test
    @Override
    public void update() {
        super.update();
    }

    @Test
    @Override
    public void getAll() {
        super.getAll();
    }
}
