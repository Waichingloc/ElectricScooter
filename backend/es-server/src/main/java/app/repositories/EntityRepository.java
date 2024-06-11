package app.repositories;


import app.models.Scooter;

import java.util.List;

public interface EntityRepository<E extends Identifiable> {
    List<E> findAll();

    E findIdById(long id);
    E save(E entity);
    E deleteById(long id);
    List<E> findByQuery(String jpqlName, Object... params);
}
