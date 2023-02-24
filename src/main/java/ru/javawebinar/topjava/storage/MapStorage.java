package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.*;
import java.util.stream.Collectors;

public class MapStorage implements Storage<Meal> {
    private final Map<Integer, Meal> map = new HashMap<>();

    @Override
    public void add(Meal t) {
        map.put(t.getUuid(), t);
    }

    @Override
    public void delete(Integer uuid) {
        map.remove(uuid);
    }

    @Override
    public void update(Meal t) {
        map.put(t.getUuid(), t);
    }

    @Override
    public List<Meal> getAllSorted() {
        Comparator<Meal> comparator = Comparator
                .comparing(Meal::getDateTime)
                .thenComparing(Meal::getDescription)
                .thenComparing(Meal::getCalories);
        return new ArrayList<>(map.values()
                .stream()
                .sorted(comparator)
                .collect(Collectors.toList()));
    }

    @Override
    public Meal getById(Integer uuid) {
        return map.get(uuid);
    }
}
