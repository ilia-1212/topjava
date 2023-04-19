package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal created = service.create(getNew(), UserTestData.USER_ID);
        Integer key = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(key);
        assertMatch(created, newMeal);
        assertMatch(service.get(key, UserTestData.USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () -> {
            service.create(getNew(), UserTestData.USER_ID);
            service.create(getDublicate(), UserTestData.USER_ID);
        });
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, UserTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> {
            service.get(MEAL_ID, UserTestData.USER_ID);
        });
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> {
            service.delete(MEAL_NOT_FOUND_ID, UserTestData.USER_ID);
        });
    }

    @Test
    public void deleteAnotherMeal() {
        assertThrows(NotFoundException.class, () -> {
            service.delete(MEAL_ID, UserTestData.ADMIN_ID);
        });
    }

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID, UserTestData.USER_ID);
        assertMatch(meal, MealTestData.meal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> {
            service.get(MEAL_NOT_FOUND_ID, UserTestData.USER_ID);
        });
    }

    @Test
    public void getAnotherMeal() {
        assertThrows(NotFoundException.class, () -> {
            service.get(MEAL_ID, UserTestData.ADMIN_ID);
        });
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, UserTestData.USER_ID);
        assertMatch(service.get(MEAL_ID, UserTestData.USER_ID), getUpdated());
    }

    @Test
    public void updateNotFound() {
        Meal updated = getNew();
        updated.setId(MEAL_NOT_FOUND_ID);
        assertThrows(NotFoundException.class, () -> {
            service.update(updated, UserTestData.USER_ID);
        });
    }

    @Test
    public void updateAnotherMeal() {
        Meal updated = getUpdated();
        assertThrows(NotFoundException.class, () -> {
            service.update(updated, UserTestData.ADMIN_ID);
        });
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = service.getBetweenInclusive(
                LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 30),
                UserTestData.USER_ID);
        assertMatch(meals, meal3, meal2, meal1);
    }

    @Test
    public void getAllNullPeriod() {
        List<Meal> meals = service.getBetweenInclusive(
                null,
                null,
                UserTestData.USER_ID);
        assertMatch(meals, meal7, meal6, meal5, meal4, meal3, meal2, meal1);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(UserTestData.USER_ID);
        assertMatch(meals, meal7, meal6, meal5, meal4, meal3, meal2, meal1);
    }

    @Test
    public void getAllEmptyMeal() {
        List<Meal> meals = service.getAll(UserTestData.GUEST_ID);
        assertMatch(meals, Collections.emptyList());
    }

    @Test
    public void getAllAnotherUser() {
        List<Meal> meals = service.getAll(UserTestData.NOT_FOUND);
        assertMatch(meals, Collections.emptyList());
    }
}