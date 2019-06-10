package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.Salada;
import com.mycompany.myapp.service.SaladaService;
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
 * REST controller for managing Salada.
 */
@RestController
@RequestMapping("/api")
public class SaladaResource {

    private final Logger log = LoggerFactory.getLogger(SaladaResource.class);

    private static final String ENTITY_NAME = "salada";

    private final SaladaService saladaService;

    public SaladaResource(SaladaService saladaService) {
        this.saladaService = saladaService;
    }

    /**
     * POST  /saladas : Create a new salada.
     *
     * @param salada the salada to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salada, or with status 400 (Bad Request) if the salada has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/saladas")
    public ResponseEntity<Salada> createSalada(@Valid @RequestBody Salada salada) throws URISyntaxException {
        log.debug("REST request to save Salada : {}", salada);
        if (salada.getId() != null) {
            throw new BadRequestAlertException("A new salada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Salada result = saladaService.save(salada);
        return ResponseEntity.created(new URI("/api/saladas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /saladas : Updates an existing salada.
     *
     * @param salada the salada to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salada,
     * or with status 400 (Bad Request) if the salada is not valid,
     * or with status 500 (Internal Server Error) if the salada couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/saladas")
    public ResponseEntity<Salada> updateSalada(@Valid @RequestBody Salada salada) throws URISyntaxException {
        log.debug("REST request to update Salada : {}", salada);
        if (salada.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Salada result = saladaService.save(salada);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, salada.getId().toString()))
            .body(result);
    }

    /**
     * GET  /saladas : get all the saladas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of saladas in body
     */
    @GetMapping("/saladas")
    public ResponseEntity<List<Salada>> getAllSaladas(Pageable pageable) {
        log.debug("REST request to get a page of Saladas");
        Page<Salada> page = saladaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/saladas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /saladas/:id : get the "id" salada.
     *
     * @param id the id of the salada to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salada, or with status 404 (Not Found)
     */
    @GetMapping("/saladas/{id}")
    public ResponseEntity<Salada> getSalada(@PathVariable Long id) {
        log.debug("REST request to get Salada : {}", id);
        Optional<Salada> salada = saladaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salada);
    }

    /**
     * DELETE  /saladas/:id : delete the "id" salada.
     *
     * @param id the id of the salada to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/saladas/{id}")
    public ResponseEntity<Void> deleteSalada(@PathVariable Long id) {
        log.debug("REST request to delete Salada : {}", id);
        saladaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
