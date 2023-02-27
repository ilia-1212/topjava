package ru.javawebinar.topjava.storage;

import java.util.List;

public interface Storage<T> {
    T add(T t);

    void delete(int id);

    T update(T t);

    List<T> getAll();

    T getById(int id);
}
