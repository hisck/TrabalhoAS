package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.SaladaService;
import com.mycompany.myapp.domain.Salada;
import com.mycompany.myapp.repository.SaladaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Salada.
 */
@Service
@Transactional
public class SaladaServiceImpl implements SaladaService {

    private final Logger log = LoggerFactory.getLogger(SaladaServiceImpl.class);

    private final SaladaRepository saladaRepository;

    public SaladaServiceImpl(SaladaRepository saladaRepository) {
        this.saladaRepository = saladaRepository;
    }

    /**
     * Save a salada.
     *
     * @param salada the entity to save
     * @return the persisted entity
     */
    @Override
    public Salada save(Salada salada) {
        log.debug("Request to save Salada : {}", salada);
        return saladaRepository.save(salada);
    }

    /**
     * Get all the saladas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Salada> findAll(Pageable pageable) {
        log.debug("Request to get all Saladas");
        return saladaRepository.findAll(pageable);
    }


    /**
     * Get one salada by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Salada> findOne(Long id) {
        log.debug("Request to get Salada : {}", id);
        return saladaRepository.findById(id);
    }

    /**
     * Delete the salada by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Salada : {}", id);
        saladaRepository.deleteById(id);
    }
}
