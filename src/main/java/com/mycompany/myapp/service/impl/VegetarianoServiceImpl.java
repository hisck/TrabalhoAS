package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.VegetarianoService;
import com.mycompany.myapp.domain.Vegetariano;
import com.mycompany.myapp.repository.VegetarianoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Vegetariano.
 */
@Service
@Transactional
public class VegetarianoServiceImpl implements VegetarianoService {

    private final Logger log = LoggerFactory.getLogger(VegetarianoServiceImpl.class);

    private final VegetarianoRepository vegetarianoRepository;

    public VegetarianoServiceImpl(VegetarianoRepository vegetarianoRepository) {
        this.vegetarianoRepository = vegetarianoRepository;
    }

    /**
     * Save a vegetariano.
     *
     * @param vegetariano the entity to save
     * @return the persisted entity
     */
    @Override
    public Vegetariano save(Vegetariano vegetariano) {
        log.debug("Request to save Vegetariano : {}", vegetariano);
        return vegetarianoRepository.save(vegetariano);
    }

    /**
     * Get all the vegetarianos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Vegetariano> findAll(Pageable pageable) {
        log.debug("Request to get all Vegetarianos");
        return vegetarianoRepository.findAll(pageable);
    }


    /**
     * Get one vegetariano by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Vegetariano> findOne(Long id) {
        log.debug("Request to get Vegetariano : {}", id);
        return vegetarianoRepository.findById(id);
    }

    /**
     * Delete the vegetariano by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vegetariano : {}", id);
        vegetarianoRepository.deleteById(id);
    }
}
