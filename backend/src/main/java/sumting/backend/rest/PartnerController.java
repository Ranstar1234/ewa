package sumting.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sumting.backend.exceptions.PreConditionFailedException;
import sumting.backend.models.Contribution;
import sumting.backend.models.Partner;
import sumting.backend.repo.EntityRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "partners")
public class PartnerController extends AbstractEntityController<Partner> {
    @Autowired
    public PartnerController(@Qualifier("PartnersRepo") EntityRepository<Partner> repo) {
        super(repo);
    }

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
     * To check if id present
     *
     * @param key key
     * @return key exist or not
     */
    @Override
    protected boolean isKeyPresent(int key) {
        for (Partner partner : repo.findAll()) {
            if (key == partner.getKey()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get all data from repo
     *
     * @return list of data
     */
    @Override
    public List<Partner> getAllData() {
        return super.getAllData();
    }

    /**
     * Get all data with its detail from repo
     *
     * @return list of data and its detail
     */
    @Override
    public List<Partner> getAllDataDetail() {
        return super.getAllDataDetail();
    }

    /**
     * Get data with the specific key
     *
     * @param key identifier
     * @return data with the specific key
     */
    @Override
    public Partner getData(int key) {
        return super.getData(key);
    }

    @Override
    public ResponseEntity<Partner> addData(Partner detail) {
        Partner partner = new Partner();
        Partner newPartner = creatingData(partner, detail);
        repo.save(newPartner);
        return ResponseEntity.ok(newPartner);
    }

    /**
     * Update Object
     *
     * @param key    identifier
     * @param detail detail
     * @return response status and its content
     */
    @Override
    public ResponseEntity<Partner> updateData(int key, Partner detail) {
        Partner editedPartner = editingData(repo.findByKey(key), detail);
        long bodyKey = detail.getKey();

        if (key != bodyKey) {
            throw new PreConditionFailedException(String.format("The id provide in the path (%d) is not the same as " +
                            "the id in the request body (%d)",
                    key, bodyKey));
        }

        repo.save(editedPartner);
        return ResponseEntity.status(202).body(editedPartner);
    }

    /**
     * Delete object
     *
     * @param key identifier
     * @return status of response and its content
     */
    @Override
    public ResponseEntity<Partner> deleteData(int key) {
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
    public Partner creatingData(Partner target, Partner detail) {
        if (isKeyPresent(detail.getKey())) {
            target.setKey(nextKey());
        }

        if (detail.getKey() == 0) {
            target.setKey(nextKey());
        }

        target.setFirstname(detail.getFirstname());
        target.setLastname(detail.getLastname());
        target.setDescription(detail.getDescription());
        target.setCreatedAt(LocalDate.now());
        target.setCountry(detail.getCountry());
        target.setAddress(detail.getAddress());
        target.setEmail(detail.getEmail());
        target.setZipcode(detail.getZipcode());

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
    public Partner editingData(Partner target, Partner detail) {
        target.setDescription(detail.getDescription());
        target.setCountry(detail.getCountry());
        target.setAddress(detail.getAddress());
        target.setEmail(detail.getEmail());
        target.setFirstname(detail.getFirstname());
        target.setLastname(detail.getLastname());

        return target;
    }

}
