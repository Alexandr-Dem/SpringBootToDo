package com.alexandrdem.springBootTodo.repository;

import java.util.Collection;
import java.util.Optional;
/**
 * @author AlexanderDementev on 02.09.2021
 */
public interface CommonRepository<T> {

    T save(T domain);
    Iterable<T> save(Collection<T> domains);
    void delete(T domain);
    Optional<T> findById(String id);
    Iterable<T> findAll();
}
