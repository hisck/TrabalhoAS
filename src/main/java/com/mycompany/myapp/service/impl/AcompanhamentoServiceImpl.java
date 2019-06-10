package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.AcompanhamentoService;
import com.mycompany.myapp.domain.Acompanhamento;
import com.mycompany.myapp.repository.AcompanhamentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Acompanhamento.
 */
@Service
@Transactional
public class AcompanhamentoServiceImpl implements AcompanhamentoService {

    private final Logger log = LoggerFactory.getLogger(AcompanhamentoServiceImpl.class);

    private final AcompanhamentoRepository acompanhamentoRepository;

    public AcompanhamentoServiceImpl(AcompanhamentoRepository acompanhamentoRepository) {
        this.acompanhamentoRepository = acompanhamentoRepository;
    }

    /**
     * Save a acompanhamento.
     *
     * @param acompanhamento the entity to save
     * @return the persisted entity
     */
    @Override
    public Acompanhamento save(Acompanhamento acompanhamento) {
        log.debug("Request to save Acompanhamento : {}", acompanhamento);
        return acompanhamentoRepository.save(acompanhamento);
    }

    /**
     * Get all the acompanhamentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Acompanhamento> findAll(Pageable pageable) {
        log.debug("Request to get all Acompanhamentos");
        return acompanhamentoRepository.findAll(pageable);
    }


    /**
     * Get one acompanhamento by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Acompanhamento> findOne(Long id) {
        log.debug("Request to get Acompanhamento : {}", id);
        return acompanhamentoRepository.findById(id);
    }

    /**
     * Delete the acompanhamento by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Acompanhamento : {}", id);
        acompanhamentoRepository.deleteById(id);
    }
}
