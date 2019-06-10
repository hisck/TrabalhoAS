package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Sobremesa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Sobremesa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SobremesaRepository extends JpaRepository<Sobremesa, Long> {

}
