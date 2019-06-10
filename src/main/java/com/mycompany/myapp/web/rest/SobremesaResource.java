package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.Sobremesa;
import com.mycompany.myapp.service.SobremesaService;
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
 * REST controller for managing Sobremesa.
 */
@RestController
@RequestMapping("/api")
public class SobremesaResource {

    private final Logger log = LoggerFactory.getLogger(SobremesaResource.class);

    private static final String ENTITY_NAME = "sobremesa";

    private final SobremesaService sobremesaService;

    public SobremesaResource(SobremesaService sobremesaService) {
        this.sobremesaService = sobremesaService;
    }

    /**
     * POST  /sobremesas : Create a new sobremesa.
     *
     * @param sobremesa the sobremesa to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sobremesa, or with status 400 (Bad Request) if the sobremesa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sobremesas")
    public ResponseEntity<Sobremesa> createSobremesa(@Valid @RequestBody Sobremesa sobremesa) throws URISyntaxException {
        log.debug("REST request to save Sobremesa : {}", sobremesa);
        if (sobremesa.getId() != null) {
            throw new BadRequestAlertException("A new sobremesa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sobremesa result = sobremesaService.save(sobremesa);
        return ResponseEntity.created(new URI("/api/sobremesas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sobremesas : Updates an existing sobremesa.
     *
     * @param sobremesa the sobremesa to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sobremesa,
     * or with status 400 (Bad Request) if the sobremesa is not valid,
     * or with status 500 (Internal Server Error) if the sobremesa couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sobremesas")
    public ResponseEntity<Sobremesa> updateSobremesa(@Valid @RequestBody Sobremesa sobremesa) throws URISyntaxException {
        log.debug("REST request to update Sobremesa : {}", sobremesa);
        if (sobremesa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sobremesa result = sobremesaService.save(sobremesa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sobremesa.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sobremesas : get all the sobremesas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sobremesas in body
     */
    @GetMapping("/sobremesas")
    public ResponseEntity<List<Sobremesa>> getAllSobremesas(Pageable pageable) {
        log.debug("REST request to get a page of Sobremesas");
        Page<Sobremesa> page = sobremesaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sobremesas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /sobremesas/:id : get the "id" sobremesa.
     *
     * @param id the id of the sobremesa to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sobremesa, or with status 404 (Not Found)
     */
    @GetMapping("/sobremesas/{id}")
    public ResponseEntity<Sobremesa> getSobremesa(@PathVariable Long id) {
        log.debug("REST request to get Sobremesa : {}", id);
        Optional<Sobremesa> sobremesa = sobremesaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sobremesa);
    }

    /**
     * DELETE  /sobremesas/:id : delete the "id" sobremesa.
     *
     * @param id the id of the sobremesa to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sobremesas/{id}")
    public ResponseEntity<Void> deleteSobremesa(@PathVariable Long id) {
        log.debug("REST request to delete Sobremesa : {}", id);
        sobremesaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
