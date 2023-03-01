package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
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

    private void fillStorage() {
        this.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                "Завтрак", 500));
        this.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0),
                "Обед", 1000));
        this.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0),
                "Ужин", 500));
        this.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),
                "Еда на граничное значение", 100));
        this.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
                "Завтрак", 1000));
        this.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
                "Обед", 500));
        this.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0),
                "Ужин", 410));
    }

    @Override
    public Meal add(Meal meal) {
        int key = nextId();
        meal.setId(key);
        mealStorage.put(key, meal);
        return meal;
    }

    private int nextId() {
        return mealId.incrementAndGet();
    }

    @Override
    public void delete(int id) {
        mealStorage.remove(id);
    }

    @Override
    public Meal update(Meal meal) {
        return mealStorage.replace(meal.getId(), meal) != null ? meal : null;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealStorage.values());
    }

    @Override
    public Meal getById(int id) {
        return mealStorage.get(id);
    }

}
