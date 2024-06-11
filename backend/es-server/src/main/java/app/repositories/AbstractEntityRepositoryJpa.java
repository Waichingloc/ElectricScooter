package app.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
public abstract class AbstractEntityRepositoryJpa<E extends Identifiable>
        implements EntityRepository<E> {

        @PersistenceContext
        protected EntityManager entityManager;

        private Class<E> theEntityClass;

        public AbstractEntityRepositoryJpa(Class<E> entityClass){
            this.theEntityClass = entityClass;
            System.out.println("Created " + this.getClass().getName() + "<" + this.theEntityClass.getSimpleName() + ">");
        };
}
