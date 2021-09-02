package com.alexandrdem.springBootTodo.repository;

import java.util.Collection;
/**
 * @author AlexanderDementev on 02.09.2021
 */
public interface CommonRepository<T> {

    T save(T domain);
    Iterable<T> save(Collection<T> domains);
    void delete(T domain);
    T findById(String id);
    Iterable<T> findAll();
}
