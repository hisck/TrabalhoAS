package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.Vegetariano;
import com.mycompany.myapp.service.VegetarianoService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Vegetariano.
 */
@RestController
@RequestMapping("/api")
public class VegetarianoResource {

    private final Logger log = LoggerFactory.getLogger(VegetarianoResource.class);

    private static final String ENTITY_NAME = "vegetariano";

    private final VegetarianoService vegetarianoService;

    public VegetarianoResource(VegetarianoService vegetarianoService) {
        this.vegetarianoService = vegetarianoService;
    }

    /**
     * POST  /vegetarianos : Create a new vegetariano.
     *
     * @param vegetariano the vegetariano to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vegetariano, or with status 400 (Bad Request) if the vegetariano has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vegetarianos")
    public ResponseEntity<Vegetariano> createVegetariano(@Valid @RequestBody Vegetariano vegetariano) throws URISyntaxException {
        log.debug("REST request to save Vegetariano : {}", vegetariano);
        if (vegetariano.getId() != null) {
            throw new BadRequestAlertException("A new vegetariano cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vegetariano result = vegetarianoService.save(vegetariano);
        return ResponseEntity.created(new URI("/api/vegetarianos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vegetarianos : Updates an existing vegetariano.
     *
     * @param vegetariano the vegetariano to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vegetariano,
     * or with status 400 (Bad Request) if the vegetariano is not valid,
     * or with status 500 (Internal Server Error) if the vegetariano couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vegetarianos")
    public ResponseEntity<Vegetariano> updateVegetariano(@Valid @RequestBody Vegetariano vegetariano) throws URISyntaxException {
        log.debug("REST request to update Vegetariano : {}", vegetariano);
        if (vegetariano.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vegetariano result = vegetarianoService.save(vegetariano);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vegetariano.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vegetarianos : get all the vegetarianos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of vegetarianos in body
     */
    @GetMapping("/vegetarianos")
    public ResponseEntity<List<Vegetariano>> getAllVegetarianos(Pageable pageable) {
        log.debug("REST request to get a page of Vegetarianos");
        Page<Vegetariano> page = vegetarianoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vegetarianos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /vegetarianos/:id : get the "id" vegetariano.
     *
     * @param id the id of the vegetariano to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vegetariano, or with status 404 (Not Found)
     */
    @GetMapping("/vegetarianos/{id}")
    public ResponseEntity<Vegetariano> getVegetariano(@PathVariable Long id) {
        log.debug("REST request to get Vegetariano : {}", id);
        Optional<Vegetariano> vegetariano = vegetarianoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vegetariano);
    }

    /**
     * DELETE  /vegetarianos/:id : delete the "id" vegetariano.
     *
     * @param id the id of the vegetariano to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vegetarianos/{id}")
    public ResponseEntity<Void> deleteVegetariano(@PathVariable Long id) {
        log.debug("REST request to delete Vegetariano : {}", id);
        vegetarianoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
