package sumting.backend.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sumting.backend.exceptions.PreConditionFailedException;
import sumting.backend.exceptions.ResourcesNotFoundException;
import sumting.backend.models.Contribution;
import sumting.backend.models.CustomViews;
import sumting.backend.models.Partner;
import sumting.backend.models.Project;
import sumting.backend.repo.EntityRepository;
import sumting.backend.repo.PartnersRepo;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/contributions")
public class ContributionController extends AbstractEntityController<Contribution> {
    @Autowired
    public ContributionController(@Qualifier("ContributionsRepo") EntityRepository<Contribution> repo) {
        super(repo);
    }

    @Autowired
    public PartnersRepo partnersRepo;

    /**
     * Create new key
     *
     * @return key
     */
    @Override
    public int nextKey() {
        int id = lastKey();
        return id + 1;
    }
    /** retrieves all projects sorted and filtered
     *
     * @param filterType what data type the data is being filtered on
     * @param filterVariable the filter variable
     * @returns all filtered sorted projects
     */
    @GetMapping(path = "/filter/keys/{filterType}/{filterVariable}")
    public List<Contribution> getSpecificKeys(@PathVariable String filterType, @PathVariable String filterVariable) {
        return this.repo.findAllKeys(filterType, filterVariable);
    }


    /** retrieves a list of all projects sorted
     *
     * @returns all filtered sorted projects
     */
    @GetMapping(path = "/sorted")
    public List<List> getAllDataDataSorted() {
        return this.repo.findAllDataSorted();
    }

    /**
     * To check if id present
     *
     * @param key id
     * @return id exist or not
     */
    @Override
    protected boolean isKeyPresent(int key) {
        for (Contribution contribution : repo.findAll()) {
            if (key == contribution.getKey()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Give the next key
     *
     * @return key
     */
    @Override
    protected int lastKey() {
        return repo.findAll().get(repo.findAll().size() - 1).getKey();
    }

    /**
     * Get all data from repo
     *
     * @return list of data
     */
    @Override
    public List<Contribution> getAllData() {
        return super.getAllData();
    }

    /** retrieves a list of all projects sorted and filtered
     *
     * @param filterType what data type the data is being filtered on
     * @param filterVariable the filter variable
     * @returns all filtered sorted projects
     */
    @GetMapping(path = "/filter/{filterType}/{filterVariable}")
    public List<List> getAllDataDataSortedAndFiltered(@PathVariable String filterType, @PathVariable String filterVariable) {
        return this.repo.getAllDataDataSortedAndFiltered(filterType, filterVariable);
    }

    /**
     * Get all data with its detail from repo
     *
     * @return list of data and its detail
     */
    @Override
    public List<Contribution> getAllDataDetail() {
        return super.getAllDataDetail();
    }

    @GetMapping(path = "partner/{partnerKey}")
    public List<Contribution> getAllDataForPartner(@PathVariable int partnerKey) {
        List<Contribution> contributions = repo.findAllByKey(partnerKey);
        return contributions;
    }

    /**
     * Get data with the specific key
     *
     * @param key identifier
     * @return data with the specific key
     */
    @Override
    public Contribution getData(int key) {
        return super.getData(key);
    }

//    /**
//     * Add new data to repo
//     *
//     * @param contribution body data
//     * @return response status and its content
//     */
//    @Override
//    public ResponseEntity<Contribution> addData(@RequestBody Contribution contribution) {
////        Contribution contribution = new Contribution();
////        Contribution newContribution = creatingData(contribution, detail);
//        repo.save(contribution);
//        return ResponseEntity.ok(contribution);
//    }

    @PostMapping(path = "/create")
    public ResponseEntity<Contribution> createContribution(@RequestBody ObjectNode node) {

        int contributionKey = node.get("contributionKey").asInt();
        LocalDate createdAt = LocalDate.parse(node.get("createdAt").asText());
        String contributionImg = node.get("contributionImg").asText();
        double latitude = node.get("latitude").asDouble();
        double longitude = node.get("longitude").asDouble();
        int projectKey = node.get("projectKey").asInt();
        int partnerKey = node.get("partnerKey").asInt();

        Contribution searchedContribution = repo.findByKey(contributionKey);
        if(searchedContribution != null) return new ResponseEntity<>(HttpStatus.CONFLICT);

        Partner partner = partnersRepo.findByKey(partnerKey);
        if(partner == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Contribution contribution = new Contribution(contributionKey, createdAt, contributionImg, latitude, longitude, partner, projectKey);
        if(contributionKey == 0) contribution.setContributionKey(lastKey());

        return new ResponseEntity<>(repo.save(contribution), HttpStatus.OK);
    }

    /**
     * Update Object
     *
     * @param key    identifier
     * @param detail detail
     * @return response status and its content
     */
    @Override
    public ResponseEntity<Contribution> updateData(int key, @RequestBody Contribution detail) {
        Contribution editedContribution = editingData(repo.findByKey(key), detail);
        long bodyKey = detail.getKey();

        if (key != bodyKey) {
            throw new PreConditionFailedException(String.format("The id provide in the path (%d) is not the same as " +
                            "the id in the request body (%d)",
                    key, bodyKey));
        }

        repo.save(editedContribution);
        return ResponseEntity.status(202).body(editedContribution);
    }

    /**
     * Delete object
     *
     * @param key identifier
     * @return status of response and its content
     */
    @Override
    public ResponseEntity<Contribution> deleteData(int key) {
        return super.deleteData(key);
    }

    /**
     * Create object
     *
     * @param target empty object
     * @param detail info
     * @return created object
     */
    @Override
    public Contribution creatingData(Contribution target, Contribution detail) {
        if (isKeyPresent(detail.getKey())) {
            target.setKey(nextKey());
        }

        if (detail.getKey() == 0) {
            target.setKey(nextKey());
        }

        target.setContributionImg(detail.getContributionImg());
        target.setCreatedAt(detail.getCreatedAt());
        target.setLatitude(detail.getLatitude());
        target.setLongitude(detail.getLongitude());
        target.setProjectKey(detail.getProjectKey());
        target.setPartnerKey(detail.getPartnerKey());

        return target;
    }

    /**
     * Edit object
     *
     * @param target empty object
     * @param detail info
     * @return edited object
     */
    @Override
    public Contribution editingData(Contribution target, Contribution detail) {
        target.setContributionImg(detail.getContributionImg());
        target.setCreatedAt(detail.getCreatedAt());
        target.setProjectKey(detail.getProjectKey());
        target.setLongitude(detail.getLongitude());
        target.setLatitude(detail.getLatitude());


        return target;
    }
}
