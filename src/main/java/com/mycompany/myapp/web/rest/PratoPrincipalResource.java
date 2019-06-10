package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.PratoPrincipal;
import com.mycompany.myapp.service.PratoPrincipalService;
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
 * REST controller for managing PratoPrincipal.
 */
@RestController
@RequestMapping("/api")
public class PratoPrincipalResource {

    private final Logger log = LoggerFactory.getLogger(PratoPrincipalResource.class);

    private static final String ENTITY_NAME = "pratoPrincipal";

    private final PratoPrincipalService pratoPrincipalService;

    public PratoPrincipalResource(PratoPrincipalService pratoPrincipalService) {
        this.pratoPrincipalService = pratoPrincipalService;
    }

    /**
     * POST  /prato-principals : Create a new pratoPrincipal.
     *
     * @param pratoPrincipal the pratoPrincipal to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pratoPrincipal, or with status 400 (Bad Request) if the pratoPrincipal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prato-principals")
    public ResponseEntity<PratoPrincipal> createPratoPrincipal(@Valid @RequestBody PratoPrincipal pratoPrincipal) throws URISyntaxException {
        log.debug("REST request to save PratoPrincipal : {}", pratoPrincipal);
        if (pratoPrincipal.getId() != null) {
            throw new BadRequestAlertException("A new pratoPrincipal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PratoPrincipal result = pratoPrincipalService.save(pratoPrincipal);
        return ResponseEntity.created(new URI("/api/prato-principals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prato-principals : Updates an existing pratoPrincipal.
     *
     * @param pratoPrincipal the pratoPrincipal to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pratoPrincipal,
     * or with status 400 (Bad Request) if the pratoPrincipal is not valid,
     * or with status 500 (Internal Server Error) if the pratoPrincipal couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prato-principals")
    public ResponseEntity<PratoPrincipal> updatePratoPrincipal(@Valid @RequestBody PratoPrincipal pratoPrincipal) throws URISyntaxException {
        log.debug("REST request to update PratoPrincipal : {}", pratoPrincipal);
        if (pratoPrincipal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PratoPrincipal result = pratoPrincipalService.save(pratoPrincipal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pratoPrincipal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prato-principals : get all the pratoPrincipals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pratoPrincipals in body
     */
    @GetMapping("/prato-principals")
    public ResponseEntity<List<PratoPrincipal>> getAllPratoPrincipals(Pageable pageable) {
        log.debug("REST request to get a page of PratoPrincipals");
        Page<PratoPrincipal> page = pratoPrincipalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prato-principals");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /prato-principals/:id : get the "id" pratoPrincipal.
     *
     * @param id the id of the pratoPrincipal to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pratoPrincipal, or with status 404 (Not Found)
     */
    @GetMapping("/prato-principals/{id}")
    public ResponseEntity<PratoPrincipal> getPratoPrincipal(@PathVariable Long id) {
        log.debug("REST request to get PratoPrincipal : {}", id);
        Optional<PratoPrincipal> pratoPrincipal = pratoPrincipalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pratoPrincipal);
    }

    /**
     * DELETE  /prato-principals/:id : delete the "id" pratoPrincipal.
     *
     * @param id the id of the pratoPrincipal to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prato-principals/{id}")
    public ResponseEntity<Void> deletePratoPrincipal(@PathVariable Long id) {
        log.debug("REST request to delete PratoPrincipal : {}", id);
        pratoPrincipalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
