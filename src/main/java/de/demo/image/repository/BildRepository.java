package de.demo.image.repository;

import de.demo.image.domain.Bild;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Bild entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BildRepository extends JpaRepository<Bild, Long> {

}
