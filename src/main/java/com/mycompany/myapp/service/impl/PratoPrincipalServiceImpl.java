package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.PratoPrincipalService;
import com.mycompany.myapp.domain.PratoPrincipal;
import com.mycompany.myapp.repository.PratoPrincipalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing PratoPrincipal.
 */
@Service
@Transactional
public class PratoPrincipalServiceImpl implements PratoPrincipalService {

    private final Logger log = LoggerFactory.getLogger(PratoPrincipalServiceImpl.class);

    private final PratoPrincipalRepository pratoPrincipalRepository;

    public PratoPrincipalServiceImpl(PratoPrincipalRepository pratoPrincipalRepository) {
        this.pratoPrincipalRepository = pratoPrincipalRepository;
    }

    /**
     * Save a pratoPrincipal.
     *
     * @param pratoPrincipal the entity to save
     * @return the persisted entity
     */
    @Override
    public PratoPrincipal save(PratoPrincipal pratoPrincipal) {
        log.debug("Request to save PratoPrincipal : {}", pratoPrincipal);
        return pratoPrincipalRepository.save(pratoPrincipal);
    }

    /**
     * Get all the pratoPrincipals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PratoPrincipal> findAll(Pageable pageable) {
        log.debug("Request to get all PratoPrincipals");
        return pratoPrincipalRepository.findAll(pageable);
    }


    /**
     * Get one pratoPrincipal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PratoPrincipal> findOne(Long id) {
        log.debug("Request to get PratoPrincipal : {}", id);
        return pratoPrincipalRepository.findById(id);
    }

    /**
     * Delete the pratoPrincipal by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PratoPrincipal : {}", id);
        pratoPrincipalRepository.deleteById(id);
    }
}
