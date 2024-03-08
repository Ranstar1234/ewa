package sumting.backend.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sumting.backend.exceptions.ResourcesNotFoundException;
import sumting.backend.models.CustomViews;
import sumting.backend.repo.EntityRepository;

import java.util.List;

public abstract class AbstractEntityController<E> implements EntityController<E> {
    protected EntityRepository<E> repo;

    public AbstractEntityController(EntityRepository<E> repo) {
        this.repo = repo;
    }

    /**
     * Create new key
     *
     * @return key
     */
    public int nextKey() {
        return 0;
    }

    /**
     * Give the next key
     *
     * @return key
     */
    protected int lastKey() {
        return 0;
    }

    /**
     * To check if id present
     *
     * @param key key
     * @return key exist or not
     */
    protected boolean isKeyPresent(int key) {
        return false;
    }

    /**
     * Get all data from repo
     *
     * @return list of data
     */
    @Override
    @GetMapping
    @JsonView(CustomViews.Summary.class)
    public List<E> getAllData() {
        return this.repo.findAll();
    }

    /**
     * Get all data with its detail from repo
     *
     * @return list of data and its detail
     */
    @Override
    @GetMapping(path = "detail")
    public List<E> getAllDataDetail() {
        return this.repo.findAll();
    }

    /**
     * Get data with the specific key
     *
     * @param key identifier
     * @return data with the specific key
     */
    @Override
    @GetMapping(path = "{key}")
    public E getData(@PathVariable int key) {
        return this.repo.findByKey(key);
    }

    /**
     * Add new data to repo
     *
     * @param detail body data
     * @return response status and its content
     */
    @Override
    @PostMapping
    public ResponseEntity<E> addData(E detail) {
        return null;
    }

    /**
     * Update Object
     *
     * @param key    identifier
     * @param detail detail
     * @return response status and its content
     */
    @Override
    @PutMapping(path = "{key}")
    public ResponseEntity<E> updateData(@PathVariable int key, E detail) {
        return null;
    }

    /**
     * Delete object
     *
     * @param key identifier
     * @return status of response and its content
     */
    @Override
    @DeleteMapping(path = "{key}")
    public ResponseEntity<E> deleteData(@PathVariable int key) {
        E target = repo.findByKey(key);
        if (target == null) {
            throw new ResourcesNotFoundException("Can't find the object with the provided id = " + key);
        }

        repo.deleteByKey(key);
        return ResponseEntity.ok(target);
    }

    /**
     * Create object
     *
     * @param target empty object
     * @param detail info
     * @return created object
     */
    @Override
    public E creatingData(E target, E detail) {
        return null;
    }

    /**
     * Edit object
     *
     * @param target empty object
     * @param detail info
     * @return edited object
     */
    @Override
    public E editingData(E target, E detail) {
        return null;
    }
}
