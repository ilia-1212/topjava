package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealStorage implements Storage<Meal> {
    private final Map<Integer, Meal> mealStorage = new ConcurrentHashMap<>();
    private final AtomicInteger mealId = new AtomicInteger(0);

    {
        fillStorage();
    }

    private Integer nextId() {
        return mealId.incrementAndGet();
    }

    @Override
    public Meal add(Meal meal) {
        Integer key = nextId();
        meal.setId(key);
        mealStorage.put(key, meal);
        return meal;
    }

    @Override
    public void delete(Integer id) {
        mealStorage.remove(id);
    }

    @Override
    public Meal update(Meal meal) {
        return (mealStorage.replace(meal.getId(), meal) != null ? meal : null);
    }

    @Override
    public List<Meal> getAll() {
        return (new ArrayList<>(mealStorage.values()));
    }

    @Override
    public Meal getById(Integer id) {
        return mealStorage.get(id);
    }

    private void fillStorage() {
        List<Meal> meals = defaultMealList();
        for (Meal meal : meals) {
            mealStorage.putIfAbsent(meal.getId(), meal);
        }
    }

    private List<Meal> defaultMealList() {
        return Arrays.asList(
                new Meal(nextId(),
                        LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                        "Завтрак", 500),
                new Meal(nextId(),
                        LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0),
                        "Обед", 1000),
                new Meal(nextId(),
                        LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0),
                        "Ужин", 500),
                new Meal(nextId(),
                        LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),
                        "Еда на граничное значение", 100),
                new Meal(nextId(),
                        LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
                        "Завтрак", 1000),
                new Meal(nextId(),
                        LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
                        "Обед", 500),
                new Meal(nextId(),
                        LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0),
                        "Ужин", 410)
        );
    }
}
