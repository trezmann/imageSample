package de.demo.image.repository;

import de.demo.image.domain.Kunde;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Kunde entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KundeRepository extends JpaRepository<Kunde, Long> {

}
