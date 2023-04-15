package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
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
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> mealMap = repository.getOrDefault(userId, new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            Meal newMeal = mealMap.computeIfAbsent(meal.getId(), (id) -> meal);
            repository.computeIfAbsent(userId, (id) -> mealMap);
            return newMeal;
        }
        // handle case: update, but not present in storage
        Meal newMeal = mealMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        repository.computeIfPresent(userId, (id, oldMeal) -> mealMap);
        return newMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.getOrDefault(userId, new ConcurrentHashMap<>()).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.getOrDefault(userId, new ConcurrentHashMap<>()).get(id);
    }

    @Override
    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        Map<Integer, Meal> mapMeal = repository.getOrDefault(userId, new ConcurrentHashMap<>());
        return mapMeal
                .values()
                .stream()
                .filter(meal -> DateTimeUtil.isBetweenClose(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

