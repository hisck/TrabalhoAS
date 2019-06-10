package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Cardapio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Cardapio.
 */
public interface CardapioService {

    /**
     * Save a cardapio.
     *
     * @param cardapio the entity to save
     * @return the persisted entity
     */
    Cardapio save(Cardapio cardapio);

    /**
     * Get all the cardapios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Cardapio> findAll(Pageable pageable);

    /**
     * Get all the Cardapio with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Cardapio> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" cardapio.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Cardapio> findOne(Long id);

    /**
     * Delete the "id" cardapio.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
