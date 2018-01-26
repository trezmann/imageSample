package de.demo.image.service;

import de.demo.image.service.dto.MandantDTO;
import java.util.List;

/**
 * Service Interface for managing Mandant.
 */
public interface MandantService {

    /**
     * Save a mandant.
     *
     * @param mandantDTO the entity to save
     * @return the persisted entity
     */
    MandantDTO save(MandantDTO mandantDTO);

    /**
     * Get all the mandants.
     *
     * @return the list of entities
     */
    List<MandantDTO> findAll();

    /**
     * Get the "id" mandant.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MandantDTO findOne(Long id);

    /**
     * Delete the "id" mandant.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
