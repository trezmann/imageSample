package de.demo.image.service;

import de.demo.image.service.dto.BildDTO;
import java.util.List;

/**
 * Service Interface for managing Bild.
 */
public interface BildService {

    /**
     * Save a bild.
     *
     * @param bildDTO the entity to save
     * @return the persisted entity
     */
    BildDTO save(BildDTO bildDTO);

    /**
     * Get all the bilds.
     *
     * @return the list of entities
     */
    List<BildDTO> findAll();

    /**
     * Get the "id" bild.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BildDTO findOne(Long id);

    /**
     * Delete the "id" bild.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
