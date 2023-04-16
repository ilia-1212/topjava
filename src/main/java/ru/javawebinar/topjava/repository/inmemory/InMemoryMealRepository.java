package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.firstUserMeals.forEach(meal -> save(meal, 1));
        MealsUtil.secondUserMeals.forEach(meal -> save(meal, 2));
    }

    @Override
    public synchronized Meal save(Meal meal, int userId) {
        Map<Integer, Meal> mealMap;
        if (repository.get(userId) == null) {
            mealMap = new ConcurrentHashMap<>();
        } else {
            mealMap = repository.get(userId);
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealMap.put(meal.getId(), meal);
            repository.put(userId, mealMap);
            return meal;
        } else {
            return mealMap.put(meal.getId(), meal);
        }
        // handle case: update, but not present in storage
        //repository.computeIfPresent(userId, (id, oldMeal) -> mealMap);
        //return mealMap.get(meal.getId());
    }

    @Override
    public boolean delete(int id, int userId) {
        if (repository.get(userId) == null) {
            return false;
        }
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        if (repository.get(userId) == null) {
            return null;
        }
        return repository.get(userId).get(id);
    }

    @Override
    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        if (repository.get(userId) == null) {
            return new ArrayList<>();
        }
        return repository.get(userId)
                .values()
                .stream()
                .filter(meal -> DateTimeUtil.isBetweenClose(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

