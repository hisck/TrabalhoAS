package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Vegetariano;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Vegetariano entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VegetarianoRepository extends JpaRepository<Vegetariano, Long> {

}
