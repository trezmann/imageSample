package de.demo.image.service;

import de.demo.image.service.dto.KundeDTO;
import java.util.List;

/**
 * Service Interface for managing Kunde.
 */
public interface KundeService {

    /**
     * Save a kunde.
     *
     * @param kundeDTO the entity to save
     * @return the persisted entity
     */
    KundeDTO save(KundeDTO kundeDTO);

    /**
     * Get all the kundes.
     *
     * @return the list of entities
     */
    List<KundeDTO> findAll();

    /**
     * Get the "id" kunde.
     *
     * @param id the id of the entity
     * @return the entity
     */
    KundeDTO findOne(Long id);

    /**
     * Delete the "id" kunde.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
