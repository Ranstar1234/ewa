package sumting.backend.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface EntityController<E> {
    /**
     * Get all data from repo
     *
     * @return list of data
     */
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    List<E> getAllData();

    /**
     * Get all data with its detail from repo
     *
     * @return list of data and its detail
     */
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    List<E> getAllDataDetail();

    /**
     * Get data with the specific key
     *
     * @param key identifier
     * @return data with the specific key
     */
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    E getData(@PathVariable int key);

    /**
     * Add new data to repo
     *
     * @param detail body data
     * @return response status and its content
     */
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<E> addData(@RequestBody E detail);

    /**
     * Update Object
     *
     * @param key    identifier
     * @param detail body data
     * @return response status and its content
     */
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<E> updateData(@PathVariable int key, @RequestBody E detail);

    /**
     * Delete object
     *
     * @param key identifier
     * @return status of response and its content
     */
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<E> deleteData(@PathVariable int key);

    /**
     * Create object
     *
     * @param target empty object
     * @param detail info
     * @return created object
     */
    E creatingData(E target, @RequestBody E detail);

    /**
     * Edit object
     *
     * @param target empty object
     * @param detail info
     * @return edited object
     */
    E editingData(E target, @RequestBody E detail);
}
