package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Acompanhamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Acompanhamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcompanhamentoRepository extends JpaRepository<Acompanhamento, Long> {

}
