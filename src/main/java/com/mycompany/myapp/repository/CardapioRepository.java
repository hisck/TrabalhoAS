package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Cardapio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Cardapio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CardapioRepository extends JpaRepository<Cardapio, Long> {

    @Query(value = "select distinct cardapio from Cardapio cardapio left join fetch cardapio.principals left join fetch cardapio.acompanhamentos left join fetch cardapio.saladas left join fetch cardapio.vegetarianos left join fetch cardapio.sobremesas",
        countQuery = "select count(distinct cardapio) from Cardapio cardapio")
    Page<Cardapio> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct cardapio from Cardapio cardapio left join fetch cardapio.principals left join fetch cardapio.acompanhamentos left join fetch cardapio.saladas left join fetch cardapio.vegetarianos left join fetch cardapio.sobremesas")
    List<Cardapio> findAllWithEagerRelationships();

    @Query("select cardapio from Cardapio cardapio left join fetch cardapio.principals left join fetch cardapio.acompanhamentos left join fetch cardapio.saladas left join fetch cardapio.vegetarianos left join fetch cardapio.sobremesas where cardapio.id =:id")
    Optional<Cardapio> findOneWithEagerRelationships(@Param("id") Long id);

}
