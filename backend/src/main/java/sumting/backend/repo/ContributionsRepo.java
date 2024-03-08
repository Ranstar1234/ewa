package sumting.backend.repo;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import sumting.backend.models.Contribution;
import sumting.backend.models.Partner;
import sumting.backend.models.Project;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository("ContributionsRepo")
public class ContributionsRepo extends AbstractEntityRepository<Contribution> {
    public ContributionsRepo() {
        super(Contribution.class);
    }

    /**
     * Find all data
     *
     * @return all data
     */
    @Override
    public List<Contribution> findAll() {
        TypedQuery<Contribution> query = em.createQuery("SELECT c FROM S2_Contribution c ", Contribution.class);
        return query.getResultList();
    }

    /**
     * Finds all contributions for a partner
     *
     * @param partnerKey Which partner's contributions to find
     * @return all contributions made by a partner
     */
    public List<Contribution> findAllByKey(int partnerKey) {
        TypedQuery<Contribution> query = em.createQuery("SELECT con FROM S2_Contribution con WHERE con.partnerKey = " + partnerKey + "", Contribution.class);
        return query.getResultList();
    }

    /**
     * retrieves all contributions filtered that are similar to the given key (filter Variable)
     *
     * @param filterType     what data type the data is being filtered on
     * @param filterVariable the filter variable
     * @returns all filtered contributions
     */
    @Override
    public List<Contribution> findAllKeys(String filterType, String filterVariable) {
        TypedQuery<Contribution> query = em.createQuery("SELECT con FROM S2_Contribution con WHERE CAST(con.contributionKey AS string) LIKE '%" + filterVariable + "%' order by con.contributionKey ASC", Contribution.class);
        return query.getResultList();
    }

    /**
     * retrieves all projects sorted and filtered(filter is optional, will return all sorted projects if either param is null)
     *
     * @param filterType     what data type the data is being filtered on
     * @param filterVariable the filter variable
     * @returns all (filtered) sorted projects
     */
    public List<Project> findAllProjectKeys(String filterType, String filterVariable) {
        if (filterType == null || filterVariable == null) {
            TypedQuery<Project> query = em.createQuery("SELECT DISTINCT pro FROM S2_Project pro order by pro.projectKey asc", Project.class);
            return query.getResultList();
        } else {
            TypedQuery<Project> query = em.createQuery("SELECT DISTINCT pro FROM S2_Project pro WHERE pro." + filterType + " like '" + filterVariable + "%' order by pro.projectKey asc", Project.class);
            return query.getResultList();
        }
    }

    /**
     * retrieves all projects sorted into Lists
     *
     * @returns all (filtered) sorted projects into a List
     */

    public List<List> findAllDataSorted() {
        List<Project> projectDescriptions = this.findAllProjectKeys(null, null);
        List<List> sortedData = new ArrayList<>();
        for (int i = 0; i < projectDescriptions.size(); i++) {
            TypedQuery<Contribution> query = em.createQuery("SELECT pro FROM S2_Contribution pro WHERE pro.projectKey= " + projectDescriptions.get(i).getKey() + " order by createdAt ASC ", Contribution.class);
            sortedData.add(query.getResultList());
        }
        return sortedData;
    }

    /**
     * retrieves all projects sorted and filtered
     *
     * @param filterType     what data type the data is being filtered on
     * @param filterVariable the filter variable
     * @returns all filtered sorted projects
     */

    public List<List> getAllDataDataSortedAndFiltered(String filterType, String filterVariable) {
        List<Project> projectDescriptions = this.findAllProjectKeys(filterType, filterVariable);
        List<List> sortedData = new ArrayList<>();
        for (int i = 0; i < projectDescriptions.size(); i++) {
            TypedQuery<Contribution> query = em.createQuery("SELECT pro FROM S2_Contribution pro WHERE pro.projectKey= " + projectDescriptions.get(i).getKey() + " order by createdAt ASC ", Contribution.class);
            sortedData.add(query.getResultList());
        }
        return sortedData;
    }

    /**
     * Find a specific data with the given key
     *
     * @param key identifier
     * @return data with the given key
     */
    @Override
    public Contribution findByKey(int key) {
        return super.findByKey(key);
    }

    /**
     * Save data to repo
     *
     * @param obj data
     * @return saved data
     */
    @Override
    public Contribution save(Contribution obj) {
        return super.save(obj);
    }

    /**
     * Delete a specific data with the given key from the repo
     *
     * @param key identifier
     * @return data with the given key
     */
    @Override
    public Contribution deleteByKey(int key) {
        return super.deleteByKey(key);
    }


}
