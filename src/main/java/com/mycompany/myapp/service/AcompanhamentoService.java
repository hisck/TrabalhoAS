package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Acompanhamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Acompanhamento.
 */
public interface AcompanhamentoService {

    /**
     * Save a acompanhamento.
     *
     * @param acompanhamento the entity to save
     * @return the persisted entity
     */
    Acompanhamento save(Acompanhamento acompanhamento);

    /**
     * Get all the acompanhamentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Acompanhamento> findAll(Pageable pageable);


    /**
     * Get the "id" acompanhamento.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Acompanhamento> findOne(Long id);

    /**
     * Delete the "id" acompanhamento.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
