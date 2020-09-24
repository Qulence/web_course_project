package dao;

import java.util.Optional;

public interface Dao<T, ID> {
    void save(T entity);

    void update(T entity);

    void deleteById(ID id);

    Iterable<T> getAll();

    Optional<T> getById(ID id);
}