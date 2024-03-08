package sumting.backend.repo;

import org.springframework.stereotype.Repository;
import sumting.backend.models.Partner;
import sumting.backend.models.Project;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository("PartnersRepo")
public class PartnersRepo extends AbstractEntityRepository<Partner> {
    public PartnersRepo() {
        super(Partner.class);
    }

    /**
     * Find all data
     *
     * @return all data
     */
    @Override
    public List<Partner> findAll() {
        TypedQuery<Partner> query = em.createQuery("SELECT part FROM S2_Partner part", Partner.class);
        return query.getResultList();
    }

    @Override
    public List<Partner> findAllKeys(String filterType, String filterVariable) {
        return null;
    }

    @Override
    public List<List> findAllDataSorted() {
        return null;
    }

    /**
     * Find a specific data with the given key
     *
     * @param key identifier
     * @return data with the given key
     */
    @Override
    public Partner findByKey(int key) {
        return super.findByKey(key);
    }

    /**
     * Save data to repo
     *
     * @param obj data
     * @return saved data
     */
    @Override
    public Partner save(Partner obj) {
        return super.save(obj);
    }

    /**
     * Delete a specific data with the given key from the repo
     *
     * @param key identifier
     * @return data with the given key
     */
    @Override
    public Partner deleteByKey(int key) {
        return super.deleteByKey(key);
    }

    @Override
    public List<List> getAllDataDataSortedAndFiltered(String filterType, String filterVariable) {
        return null;
    }
}
