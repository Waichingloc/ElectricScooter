package app.repositories;

import app.models.Scooter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository("SCOOTERS.JPA")
@Transactional
public class ScootersRepositoryJpa extends AbstractEntityRepositoryJpa<Scooter> {

    @PersistenceContext
    EntityManager entityManager;

    public ScootersRepositoryJpa() {
        super(Scooter.class);
    }

    @Override
    public List<Scooter> findAll() {
        TypedQuery<Scooter> query = this.entityManager.createQuery(
                "select s from Scooter s", Scooter.class);
        return query.getResultList();
    }

    @Override
    public Scooter findIdById(long id) {
        return entityManager.find(Scooter.class, id);
    }

    @Override
    public Scooter save(Scooter scooter) {
        return entityManager.merge(scooter);
    }

    @Override
    public Scooter deleteById(long id) {
        Scooter scooter = findIdById(id);
        entityManager.remove(scooter);
        return scooter;
    }

    @Override
    public List<Scooter> findByQuery(String jpqlName, Object... params) {
        TypedQuery<Scooter> query = this.entityManager.createNamedQuery(jpqlName, Scooter.class);

        for (int i = 0; i < params.length; i++) {
            query.setParameter(i+1, params[i]);
        }
        return query.getResultList();
    }
}
