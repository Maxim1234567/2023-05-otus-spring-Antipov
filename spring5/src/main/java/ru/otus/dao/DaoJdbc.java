package ru.otus.dao;

import java.util.List;

public interface DaoJdbc<T> {
    int count();

    T insert(T value);

    T update(T value);

    T getById(long id);

    List<T> findAllById(Iterable<Long> ids);

    List<T> getAll();

    void deleteById(long id);
}
