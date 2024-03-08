package sumting.backend.repo;

import org.springframework.stereotype.Repository;
import sumting.backend.models.Partner;
import sumting.backend.models.Contribution;
import sumting.backend.models.Project;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository("ProjectsRepo")
public class ProjectsRepo extends AbstractEntityRepository<Project> {
    public ProjectsRepo() {
        super(Project.class);
    }

    /**
     * Find all data
     *
     * @return all data
     */
    @Override
    public List<Project> findAll() {
        TypedQuery<Project> query = em.createQuery("SELECT pro FROM S2_Project pro", Project.class);
        return query.getResultList();
    }


    public List<String> findAllProjectNames() {
        TypedQuery<String> query = em.createQuery("SELECT DISTINCT pro.bucket FROM S2_Project pro", String.class);
        return query.getResultList();
    }
    /**
     * Find all data sorted by project discription for easier sorting
     *
     * @return all different types of projects
     */

    @Override
    public List<List> findAllDataSorted() {
        return null;
    }

    /** retrieves all project filtered(filter is optional, will return all sorted projects if either param is null)
     *
     * @param filterType what data type the data is being filtered on
     * @param filterVariable the filter variable
     * @returns all (filtered) sorted projects
     */

    public List<Project> findAllKeys(String filterType, String filterVariable) {
        if(filterType ==null||filterVariable==null){
        TypedQuery<Project> query = em.createQuery("SELECT DISTINCT pro FROM S2_Project pro order by pro.projectKey asc", Project.class);
        return query.getResultList();}

        else{
            TypedQuery<Project> query = em.createQuery("SELECT DISTINCT pro FROM S2_Project pro WHERE "+filterType+" like '"+ filterVariable+ "%' order by pro.projectKey asc", Project.class);
            return query.getResultList();}
        }

    /**
     * Find a specific data with the given key
     *
     * @param key identifier
     * @return data with the given key
     */
    @Override
    public Project findByKey(int key) {
        return super.findByKey(key);
    }

    /**
     * Save data to repo
     *
     * @param obj data
     * @return saved data
     */
    @Override
    public Project save(Project obj) {
        return super.save(obj);
    }

    /**
     * Delete a specific data with the given key from the repo
     *
     * @param key identifier
     * @return data with the given key
     */
    @Override
    public Project deleteByKey(int key) {
        return super.deleteByKey(key);
    }

    @Override
    public List<List> getAllDataDataSortedAndFiltered(String filterType, String filterVariable) {
        return null;
    }
}
