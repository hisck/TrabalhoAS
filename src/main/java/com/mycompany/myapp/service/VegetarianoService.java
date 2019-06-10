package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Vegetariano;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Vegetariano.
 */
public interface VegetarianoService {

    /**
     * Save a vegetariano.
     *
     * @param vegetariano the entity to save
     * @return the persisted entity
     */
    Vegetariano save(Vegetariano vegetariano);

    /**
     * Get all the vegetarianos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Vegetariano> findAll(Pageable pageable);


    /**
     * Get the "id" vegetariano.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Vegetariano> findOne(Long id);

    /**
     * Delete the "id" vegetariano.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
