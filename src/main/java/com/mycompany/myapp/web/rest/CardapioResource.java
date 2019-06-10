package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.Cardapio;
import com.mycompany.myapp.service.CardapioService;
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
 * REST controller for managing Cardapio.
 */
@RestController
@RequestMapping("/api")
public class CardapioResource {

    private final Logger log = LoggerFactory.getLogger(CardapioResource.class);

    private static final String ENTITY_NAME = "cardapio";

    private final CardapioService cardapioService;

    public CardapioResource(CardapioService cardapioService) {
        this.cardapioService = cardapioService;
    }

    /**
     * POST  /cardapios : Create a new cardapio.
     *
     * @param cardapio the cardapio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cardapio, or with status 400 (Bad Request) if the cardapio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cardapios")
    public ResponseEntity<Cardapio> createCardapio(@Valid @RequestBody Cardapio cardapio) throws URISyntaxException {
        log.debug("REST request to save Cardapio : {}", cardapio);
        if (cardapio.getId() != null) {
            throw new BadRequestAlertException("A new cardapio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cardapio result = cardapioService.save(cardapio);
        return ResponseEntity.created(new URI("/api/cardapios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cardapios : Updates an existing cardapio.
     *
     * @param cardapio the cardapio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cardapio,
     * or with status 400 (Bad Request) if the cardapio is not valid,
     * or with status 500 (Internal Server Error) if the cardapio couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cardapios")
    public ResponseEntity<Cardapio> updateCardapio(@Valid @RequestBody Cardapio cardapio) throws URISyntaxException {
        log.debug("REST request to update Cardapio : {}", cardapio);
        if (cardapio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cardapio result = cardapioService.save(cardapio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cardapio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cardapios : get all the cardapios.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of cardapios in body
     */
    @GetMapping("/cardapios")
    public ResponseEntity<List<Cardapio>> getAllCardapios(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Cardapios");
        Page<Cardapio> page;
        if (eagerload) {
            page = cardapioService.findAllWithEagerRelationships(pageable);
        } else {
            page = cardapioService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/cardapios?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /cardapios/:id : get the "id" cardapio.
     *
     * @param id the id of the cardapio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cardapio, or with status 404 (Not Found)
     */
    @GetMapping("/cardapios/{id}")
    public ResponseEntity<Cardapio> getCardapio(@PathVariable Long id) {
        log.debug("REST request to get Cardapio : {}", id);
        Optional<Cardapio> cardapio = cardapioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cardapio);
    }

    /**
     * DELETE  /cardapios/:id : delete the "id" cardapio.
     *
     * @param id the id of the cardapio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cardapios/{id}")
    public ResponseEntity<Void> deleteCardapio(@PathVariable Long id) {
        log.debug("REST request to delete Cardapio : {}", id);
        cardapioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
