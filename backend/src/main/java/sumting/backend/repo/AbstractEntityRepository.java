package sumting.backend.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public abstract class AbstractEntityRepository<E> implements EntityRepository<E> {
    @PersistenceContext
    protected EntityManager em;
    private Class<E> theEntityClass;

    public AbstractEntityRepository(Class<E> theEntityClass) {
        this.theEntityClass = theEntityClass;
    }

    /**
     * Find all data
     *
     * @return all data
     */
    @Override
    public List<E> findAll() {
        return null;
    }

    /**
     * Find a specific data with the given key
     *
     * @param key identifier
     * @return data with the given key
     */
    @Override
    public E findByKey(int key) {
        return em.find(this.theEntityClass, key);
    }

    /**
     * Finds all of an object by a given key
     * @param key identifier
     * @return list with all items
     */
    @Override
    public List<E> findAllByKey(int key){
        return null;

    }

    /**
     * Save data to repo
     *
     * @param obj data
     * @return saved data
     */
    @Override
    public E save(E obj) {
        return em.merge(obj);
    }

    /**
     * Delete a specific data with the given key from the repo
     *
     * @param key identifier
     * @return data with the given key
     */
    @Override
    public E deleteByKey(int key) {
        E target = this.findByKey(key);
        em.remove(target);
        return target;
    }
}
