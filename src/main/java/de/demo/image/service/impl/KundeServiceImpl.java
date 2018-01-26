package de.demo.image.service.impl;

import de.demo.image.service.KundeService;
import de.demo.image.domain.Kunde;
import de.demo.image.repository.KundeRepository;
import de.demo.image.service.dto.KundeDTO;
import de.demo.image.service.mapper.KundeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Kunde.
 */
@Service
@Transactional
public class KundeServiceImpl implements KundeService {

    private final Logger log = LoggerFactory.getLogger(KundeServiceImpl.class);

    private final KundeRepository kundeRepository;

    private final KundeMapper kundeMapper;

    public KundeServiceImpl(KundeRepository kundeRepository, KundeMapper kundeMapper) {
        this.kundeRepository = kundeRepository;
        this.kundeMapper = kundeMapper;
    }

    /**
     * Save a kunde.
     *
     * @param kundeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public KundeDTO save(KundeDTO kundeDTO) {
        log.debug("Request to save Kunde : {}", kundeDTO);
        Kunde kunde = kundeMapper.toEntity(kundeDTO);
        kunde = kundeRepository.save(kunde);
        return kundeMapper.toDto(kunde);
    }

    /**
     * Get all the kundes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<KundeDTO> findAll() {
        log.debug("Request to get all Kundes");
        return kundeRepository.findAll().stream()
            .map(kundeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one kunde by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public KundeDTO findOne(Long id) {
        log.debug("Request to get Kunde : {}", id);
        Kunde kunde = kundeRepository.findOne(id);
        return kundeMapper.toDto(kunde);
    }

    /**
     * Delete the kunde by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Kunde : {}", id);
        kundeRepository.delete(id);
    }
}
