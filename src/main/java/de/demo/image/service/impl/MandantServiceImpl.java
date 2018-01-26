package de.demo.image.service.impl;

import de.demo.image.service.MandantService;
import de.demo.image.domain.Mandant;
import de.demo.image.repository.MandantRepository;
import de.demo.image.service.dto.MandantDTO;
import de.demo.image.service.mapper.MandantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Mandant.
 */
@Service
@Transactional
public class MandantServiceImpl implements MandantService {

    private final Logger log = LoggerFactory.getLogger(MandantServiceImpl.class);

    private final MandantRepository mandantRepository;

    private final MandantMapper mandantMapper;

    public MandantServiceImpl(MandantRepository mandantRepository, MandantMapper mandantMapper) {
        this.mandantRepository = mandantRepository;
        this.mandantMapper = mandantMapper;
    }

    /**
     * Save a mandant.
     *
     * @param mandantDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MandantDTO save(MandantDTO mandantDTO) {
        log.debug("Request to save Mandant : {}", mandantDTO);
        Mandant mandant = mandantMapper.toEntity(mandantDTO);
        mandant = mandantRepository.save(mandant);
        return mandantMapper.toDto(mandant);
    }

    /**
     * Get all the mandants.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MandantDTO> findAll() {
        log.debug("Request to get all Mandants");
        return mandantRepository.findAll().stream()
            .map(mandantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one mandant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MandantDTO findOne(Long id) {
        log.debug("Request to get Mandant : {}", id);
        Mandant mandant = mandantRepository.findOne(id);
        return mandantMapper.toDto(mandant);
    }

    /**
     * Delete the mandant by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mandant : {}", id);
        mandantRepository.delete(id);
    }
}
