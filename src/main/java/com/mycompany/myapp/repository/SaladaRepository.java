package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Salada;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Salada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaladaRepository extends JpaRepository<Salada, Long> {

}
