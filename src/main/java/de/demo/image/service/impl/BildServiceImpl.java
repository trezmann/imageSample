package de.demo.image.service.impl;

import de.demo.image.service.BildService;
import de.demo.image.domain.Bild;
import de.demo.image.repository.BildRepository;
import de.demo.image.service.dto.BildDTO;
import de.demo.image.service.mapper.BildMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Bild.
 */
@Service
@Transactional
public class BildServiceImpl implements BildService {

    private final Logger log = LoggerFactory.getLogger(BildServiceImpl.class);

    private final BildRepository bildRepository;

    private final BildMapper bildMapper;

    public BildServiceImpl(BildRepository bildRepository, BildMapper bildMapper) {
        this.bildRepository = bildRepository;
        this.bildMapper = bildMapper;
    }

    /**
     * Save a bild.
     *
     * @param bildDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BildDTO save(BildDTO bildDTO) {
        log.debug("Request to save Bild : {}", bildDTO);
        Bild bild = bildMapper.toEntity(bildDTO);
        bild = bildRepository.save(bild);
        return bildMapper.toDto(bild);
    }

    /**
     * Get all the bilds.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BildDTO> findAll() {
        log.debug("Request to get all Bilds");
        return bildRepository.findAll().stream()
            .map(bildMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one bild by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BildDTO findOne(Long id) {
        log.debug("Request to get Bild : {}", id);
        Bild bild = bildRepository.findOne(id);
        return bildMapper.toDto(bild);
    }

    /**
     * Delete the bild by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bild : {}", id);
        bildRepository.delete(id);
    }
}
