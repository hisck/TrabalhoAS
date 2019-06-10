package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.SobremesaService;
import com.mycompany.myapp.domain.Sobremesa;
import com.mycompany.myapp.repository.SobremesaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Sobremesa.
 */
@Service
@Transactional
public class SobremesaServiceImpl implements SobremesaService {

    private final Logger log = LoggerFactory.getLogger(SobremesaServiceImpl.class);

    private final SobremesaRepository sobremesaRepository;

    public SobremesaServiceImpl(SobremesaRepository sobremesaRepository) {
        this.sobremesaRepository = sobremesaRepository;
    }

    /**
     * Save a sobremesa.
     *
     * @param sobremesa the entity to save
     * @return the persisted entity
     */
    @Override
    public Sobremesa save(Sobremesa sobremesa) {
        log.debug("Request to save Sobremesa : {}", sobremesa);
        return sobremesaRepository.save(sobremesa);
    }

    /**
     * Get all the sobremesas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Sobremesa> findAll(Pageable pageable) {
        log.debug("Request to get all Sobremesas");
        return sobremesaRepository.findAll(pageable);
    }


    /**
     * Get one sobremesa by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Sobremesa> findOne(Long id) {
        log.debug("Request to get Sobremesa : {}", id);
        return sobremesaRepository.findById(id);
    }

    /**
     * Delete the sobremesa by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sobremesa : {}", id);
        sobremesaRepository.deleteById(id);
    }
}
