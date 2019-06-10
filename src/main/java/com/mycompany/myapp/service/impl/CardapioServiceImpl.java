package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.CardapioService;
import com.mycompany.myapp.domain.Cardapio;
import com.mycompany.myapp.repository.CardapioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Cardapio.
 */
@Service
@Transactional
public class CardapioServiceImpl implements CardapioService {

    private final Logger log = LoggerFactory.getLogger(CardapioServiceImpl.class);

    private final CardapioRepository cardapioRepository;

    public CardapioServiceImpl(CardapioRepository cardapioRepository) {
        this.cardapioRepository = cardapioRepository;
    }

    /**
     * Save a cardapio.
     *
     * @param cardapio the entity to save
     * @return the persisted entity
     */
    @Override
    public Cardapio save(Cardapio cardapio) {
        log.debug("Request to save Cardapio : {}", cardapio);
        return cardapioRepository.save(cardapio);
    }

    /**
     * Get all the cardapios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Cardapio> findAll(Pageable pageable) {
        log.debug("Request to get all Cardapios");
        return cardapioRepository.findAll(pageable);
    }

    /**
     * Get all the Cardapio with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Cardapio> findAllWithEagerRelationships(Pageable pageable) {
        return cardapioRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one cardapio by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Cardapio> findOne(Long id) {
        log.debug("Request to get Cardapio : {}", id);
        return cardapioRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the cardapio by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cardapio : {}", id);
        cardapioRepository.deleteById(id);
    }
}
