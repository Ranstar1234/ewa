package sumting.backend.rest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sumting.backend.exceptions.PreConditionFailedException;
import sumting.backend.models.Contribution;
import sumting.backend.models.CustomViews;
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

    // CSRF Vulnerability - Form submission endpoint without CSRF protection
    @PostMapping(path = "/vulnerableEndpoint")
    public ResponseEntity<String> vulnerableEndpoint(@RequestBody ObjectNode node) {
        // Process the request
        return ResponseEntity.ok("Request processed successfully");
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

    // Error handling with sensitive information exposure
    @GetMapping(path = "/error")
    public ResponseEntity<String> error() {
        String errorMessage = "An error occurred: database connection failed";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    // Other endpoints and methods remain unchanged...
}
