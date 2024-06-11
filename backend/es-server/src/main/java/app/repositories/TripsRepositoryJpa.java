package app.repositories;

import app.models.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import java.util.List;

@Primary
@Repository("TRIPS.JPA")
@Transactional
public class TripsRepositoryJpa extends AbstractEntityRepositoryJpa<Trip> {
    @PersistenceContext
    EntityManager entityManager;

    public TripsRepositoryJpa() {
        super(Trip.class);
    }

    @Override
    public List<Trip> findAll() {
        TypedQuery<Trip> query = this.entityManager.createQuery(
                "select t from Trip t", Trip.class);
        return query.getResultList();
    }

    @Override
    public Trip findIdById(long id) {
        return entityManager.find(Trip.class, id);
    }

    @Override
    public Trip save(Trip trip) {
        return entityManager.merge(trip);
    }

    @Override
    public Trip deleteById(long id) {
        Trip trip = findIdById(id);
        entityManager.remove(trip);
        return trip;
    }

    @Override
    public List<Trip> findByQuery(String jpqlName, Object... params) {
        TypedQuery<Trip> query = this.entityManager.createNamedQuery(jpqlName, Trip.class);

        for (int i = 0; i < params.length; i++) {
            query.setParameter(i+1, params[i]);
        }
        return query.getResultList();
    }
}
