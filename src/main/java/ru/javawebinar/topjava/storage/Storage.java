package ru.javawebinar.topjava.storage;

import java.util.List;

public interface Storage<T> {
     void add(T t);
    void delete(Integer uuid);
    void update(T t);
    List<T> getAllSorted();
    T getById(Integer uuid);
}
