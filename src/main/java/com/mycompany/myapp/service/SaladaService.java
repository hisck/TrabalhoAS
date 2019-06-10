package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Salada;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Salada.
 */
public interface SaladaService {

    /**
     * Save a salada.
     *
     * @param salada the entity to save
     * @return the persisted entity
     */
    Salada save(Salada salada);

    /**
     * Get all the saladas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Salada> findAll(Pageable pageable);


    /**
     * Get the "id" salada.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Salada> findOne(Long id);

    /**
     * Delete the "id" salada.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
