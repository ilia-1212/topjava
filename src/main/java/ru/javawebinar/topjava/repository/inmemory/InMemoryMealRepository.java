package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        if (meal.getUserId() == userId) {
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        if (get(id, userId) != null) {
            repository.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal m = repository.get(id);
        if (m != null) {
            if (m.getUserId() == userId) {
                return m;
            }
        }
        return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository
                .values()
                .stream()
                .filter(s-> userId == s.getUserId())
                .sorted(Comparator.comparing(Meal::getDateTime)
                        .reversed()).collect(Collectors.toList());
    }
}

