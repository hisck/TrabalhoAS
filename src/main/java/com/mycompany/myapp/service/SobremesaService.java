package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Sobremesa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Sobremesa.
 */
public interface SobremesaService {

    /**
     * Save a sobremesa.
     *
     * @param sobremesa the entity to save
     * @return the persisted entity
     */
    Sobremesa save(Sobremesa sobremesa);

    /**
     * Get all the sobremesas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Sobremesa> findAll(Pageable pageable);


    /**
     * Get the "id" sobremesa.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Sobremesa> findOne(Long id);

    /**
     * Delete the "id" sobremesa.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
