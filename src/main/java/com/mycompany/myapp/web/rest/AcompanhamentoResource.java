package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.Acompanhamento;
import com.mycompany.myapp.service.AcompanhamentoService;
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
 * REST controller for managing Acompanhamento.
 */
@RestController
@RequestMapping("/api")
public class AcompanhamentoResource {

    private final Logger log = LoggerFactory.getLogger(AcompanhamentoResource.class);

    private static final String ENTITY_NAME = "acompanhamento";

    private final AcompanhamentoService acompanhamentoService;

    public AcompanhamentoResource(AcompanhamentoService acompanhamentoService) {
        this.acompanhamentoService = acompanhamentoService;
    }

    /**
     * POST  /acompanhamentos : Create a new acompanhamento.
     *
     * @param acompanhamento the acompanhamento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new acompanhamento, or with status 400 (Bad Request) if the acompanhamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/acompanhamentos")
    public ResponseEntity<Acompanhamento> createAcompanhamento(@Valid @RequestBody Acompanhamento acompanhamento) throws URISyntaxException {
        log.debug("REST request to save Acompanhamento : {}", acompanhamento);
        if (acompanhamento.getId() != null) {
            throw new BadRequestAlertException("A new acompanhamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Acompanhamento result = acompanhamentoService.save(acompanhamento);
        return ResponseEntity.created(new URI("/api/acompanhamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /acompanhamentos : Updates an existing acompanhamento.
     *
     * @param acompanhamento the acompanhamento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated acompanhamento,
     * or with status 400 (Bad Request) if the acompanhamento is not valid,
     * or with status 500 (Internal Server Error) if the acompanhamento couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/acompanhamentos")
    public ResponseEntity<Acompanhamento> updateAcompanhamento(@Valid @RequestBody Acompanhamento acompanhamento) throws URISyntaxException {
        log.debug("REST request to update Acompanhamento : {}", acompanhamento);
        if (acompanhamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Acompanhamento result = acompanhamentoService.save(acompanhamento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, acompanhamento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /acompanhamentos : get all the acompanhamentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of acompanhamentos in body
     */
    @GetMapping("/acompanhamentos")
    public ResponseEntity<List<Acompanhamento>> getAllAcompanhamentos(Pageable pageable) {
        log.debug("REST request to get a page of Acompanhamentos");
        Page<Acompanhamento> page = acompanhamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/acompanhamentos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /acompanhamentos/:id : get the "id" acompanhamento.
     *
     * @param id the id of the acompanhamento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the acompanhamento, or with status 404 (Not Found)
     */
    @GetMapping("/acompanhamentos/{id}")
    public ResponseEntity<Acompanhamento> getAcompanhamento(@PathVariable Long id) {
        log.debug("REST request to get Acompanhamento : {}", id);
        Optional<Acompanhamento> acompanhamento = acompanhamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(acompanhamento);
    }

    /**
     * DELETE  /acompanhamentos/:id : delete the "id" acompanhamento.
     *
     * @param id the id of the acompanhamento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/acompanhamentos/{id}")
    public ResponseEntity<Void> deleteAcompanhamento(@PathVariable Long id) {
        log.debug("REST request to delete Acompanhamento : {}", id);
        acompanhamentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
