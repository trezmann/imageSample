package de.demo.image.repository;

import de.demo.image.domain.Mandant;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Mandant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MandantRepository extends JpaRepository<Mandant, Long> {

}
