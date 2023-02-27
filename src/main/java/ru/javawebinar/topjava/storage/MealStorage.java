package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealStorage implements Storage<Meal> {
    private final Map<Integer, Meal> mealStorage = new ConcurrentHashMap<>();
    private static Storage<Meal> storage;

    static {
        fillStorage();
    }

    @Override
    public Meal add(Meal t) {
        int key = MealsUtil.next_seq();
        mealStorage.put(key, t);
        return getById(key);
    }

    @Override
    public void delete(int id) {
        mealStorage.remove(id);
    }

    @Override
    public Meal update(Meal t) {
        int key = t.getId();
        if (mealStorage.containsKey(key)) {
            mealStorage.put(key, t);
            return getById(key);
        }
        return null;
    }

    @Override
    public List<Meal> getAll() {
        return (new ArrayList<>(mealStorage.values()));
    }

    @Override
    public Meal getById(int id) {
        return mealStorage.get(id);
    }


    public static Storage<Meal> getStorage() {
        return storage;
    }


    private static void fillStorage() {
        storage = new MealStorage();
        List<Meal> meals = defaultMealList();
        for (Meal meal : meals) {
            storage.add(meal);
        }
    }

    private static List<Meal> defaultMealList() {
        return Arrays.asList(
                new Meal(MealsUtil.next_seq(),
                        LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                        "Завтрак", 500),
                new Meal(MealsUtil.next_seq(),
                        LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0),
                        "Обед", 1000),
                new Meal(MealsUtil.next_seq(),
                        LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0),
                        "Ужин", 500),
                new Meal(MealsUtil.next_seq(),
                        LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),
                        "Еда на граничное значение", 100),
                new Meal(MealsUtil.next_seq(),
                        LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
                        "Завтрак", 1000),
                new Meal(MealsUtil.next_seq(),
                        LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
                        "Обед", 500),
                new Meal(MealsUtil.next_seq(),
                        LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0),
                        "Ужин", 410)
        );
    }

}
