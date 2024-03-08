package sumting.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sumting.backend.exceptions.PreConditionFailedException;
import sumting.backend.models.Project;
import sumting.backend.repo.ContributionsRepo;
import sumting.backend.repo.EntityRepository;

import java.util.List;

@RestController
@RequestMapping(path = "projects")
public class ProjectController extends AbstractEntityController<Project> {
    @Autowired
    public ProjectController(@Qualifier("ProjectsRepo") EntityRepository<Project> repo) {
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
        for (Project project : repo.findAll()) {
            if (key == project.getKey()) {
                return true;
            }
        }
        return false;
    }
    /** retrieves all projects sorted
     *
     * @returns all sorted projects
     */
    @GetMapping(path = "/sorted")
    public List<Project> getAllDataDataSorted() {
        return this.repo.findAllKeys(null,null);
    }
    /** retrieves all projects sorted and filtered
     *
     * @param filterType what data type the data is being filtered on
     * @param filterVariable the filter variable
     * @returns all filtered sorted projects
     */
    @GetMapping(path = "/filter/{filterType}/{filterVariable}")
    public List<Project> getAllDataDataSortedAndFiltered(@PathVariable String filterType, @PathVariable String filterVariable) {
        return this.repo.findAllKeys(filterType,filterVariable);
    }


    /**
     * Get all data from repo
     *
     * @return list of data
     */
    @Override
    public List<Project> getAllData() {
        return super.getAllData();
    }

    /**
     * Get all data with its detail from repo
     *
     * @return list of data and its detail
     */
    @Override
    public List<Project> getAllDataDetail() {
        return super.getAllDataDetail();
    }

    /**
     * Get data with the specific key
     *
     * @param key identifier
     * @return data with the specific key
     */
    @Override
    public Project getData(int key) {
        return super.getData(key);
    }

    /**
     * Update Object
     *
     * @param key    identifier
     * @param detail detail
     * @return response status and its content
     */
    @Override
    public ResponseEntity<Project> updateData(int key, Project detail) {
        Project editedProject = editingData(repo.findByKey(key), detail);
        long bodyKey = detail.getKey();

        if (key != bodyKey) {
            throw new PreConditionFailedException(String.format("The id provide in the path (%d) is not the same as " +
                            "the id in the request body (%d)",
                    key, bodyKey));
        }

        repo.save(editedProject);
        return ResponseEntity.status(202).body(editedProject);
    }

    /**
     * Delete object
     *
     * @param key identifier
     * @return status of response and its content
     */
    @Override
    public ResponseEntity<Project> deleteData(int key) {
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
    public Project creatingData(Project target, Project detail) {
        if (isKeyPresent(detail.getKey())) {
            target.setKey(nextKey());
        }

        if (detail.getKey() == 0) {
            target.setKey(nextKey());
        }

        target.setName(detail.getName());

        target.setBucket(target.getDescription());
        target.setDescription(detail.getDescription());

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
    public Project editingData(Project target, Project detail) {
        target.setBucket(target.getDescription());
        target.setDescription(detail.getDescription());
        target.setName(detail.getName());

        return target;
    }
}
