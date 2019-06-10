package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.PratoPrincipal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing PratoPrincipal.
 */
public interface PratoPrincipalService {

    /**
     * Save a pratoPrincipal.
     *
     * @param pratoPrincipal the entity to save
     * @return the persisted entity
     */
    PratoPrincipal save(PratoPrincipal pratoPrincipal);

    /**
     * Get all the pratoPrincipals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PratoPrincipal> findAll(Pageable pageable);


    /**
     * Get the "id" pratoPrincipal.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PratoPrincipal> findOne(Long id);

    /**
     * Delete the "id" pratoPrincipal.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
