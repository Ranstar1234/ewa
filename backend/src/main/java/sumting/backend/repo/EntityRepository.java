package sumting.backend.repo;

import sumting.backend.models.Contribution;
import sumting.backend.models.Project;

import java.util.List;

public interface EntityRepository<E> {
    /**
     * Find all data
     *
     * @return all data
     */
    List<E> findAll();

    /**
     * Find all data
     *
     * @return all data
     */
    List<E> findAllKeys(String filterOption, String filterVariable);
    /**
     * Find all sorted data
     *
     * @return all sorted data
     */
    List<List> findAllDataSorted();
    /**
     * Find a specific data with the given key
     *
     * @param key identifier
     * @return data with the given key
     */
    E findByKey(int key);

    /**
     * Find all data with a specific key
     * @param key identifier
     * @return list with given key
     */
    List<E> findAllByKey(int key);

    /**
     * Save data to repo
     *
     * @param obj data
     * @return saved data
     */
    E save(E obj);

    /**
     * Delete a specific data with the given key from the repo
     *
     * @param key identifier
     * @return data with the given key
     */
    E deleteByKey(int key);

    List<List> getAllDataDataSortedAndFiltered(String filterType, String filterVariable);
}
