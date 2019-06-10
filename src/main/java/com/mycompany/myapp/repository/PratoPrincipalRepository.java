package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PratoPrincipal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PratoPrincipal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PratoPrincipalRepository extends JpaRepository<PratoPrincipal, Long> {

}
