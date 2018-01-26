package de.demo.image.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.demo.image.service.KundeService;
import de.demo.image.web.rest.errors.BadRequestAlertException;
import de.demo.image.web.rest.util.HeaderUtil;
import de.demo.image.service.dto.KundeDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Kunde.
 */
@RestController
@RequestMapping("/api")
public class KundeResource {

    private final Logger log = LoggerFactory.getLogger(KundeResource.class);

    private static final String ENTITY_NAME = "kunde";

    private final KundeService kundeService;

    public KundeResource(KundeService kundeService) {
        this.kundeService = kundeService;
    }

    /**
     * POST  /kundes : Create a new kunde.
     *
     * @param kundeDTO the kundeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kundeDTO, or with status 400 (Bad Request) if the kunde has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/kundes")
    @Timed
    public ResponseEntity<KundeDTO> createKunde(@RequestBody KundeDTO kundeDTO) throws URISyntaxException {
        log.debug("REST request to save Kunde : {}", kundeDTO);
        if (kundeDTO.getId() != null) {
            throw new BadRequestAlertException("A new kunde cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KundeDTO result = kundeService.save(kundeDTO);
        return ResponseEntity.created(new URI("/api/kundes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kundes : Updates an existing kunde.
     *
     * @param kundeDTO the kundeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kundeDTO,
     * or with status 400 (Bad Request) if the kundeDTO is not valid,
     * or with status 500 (Internal Server Error) if the kundeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/kundes")
    @Timed
    public ResponseEntity<KundeDTO> updateKunde(@RequestBody KundeDTO kundeDTO) throws URISyntaxException {
        log.debug("REST request to update Kunde : {}", kundeDTO);
        if (kundeDTO.getId() == null) {
            return createKunde(kundeDTO);
        }
        KundeDTO result = kundeService.save(kundeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kundeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kundes : get all the kundes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of kundes in body
     */
    @GetMapping("/kundes")
    @Timed
    public List<KundeDTO> getAllKundes() {
        log.debug("REST request to get all Kundes");
        return kundeService.findAll();
        }

    /**
     * GET  /kundes/:id : get the "id" kunde.
     *
     * @param id the id of the kundeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kundeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/kundes/{id}")
    @Timed
    public ResponseEntity<KundeDTO> getKunde(@PathVariable Long id) {
        log.debug("REST request to get Kunde : {}", id);
        KundeDTO kundeDTO = kundeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(kundeDTO));
    }

    /**
     * DELETE  /kundes/:id : delete the "id" kunde.
     *
     * @param id the id of the kundeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/kundes/{id}")
    @Timed
    public ResponseEntity<Void> deleteKunde(@PathVariable Long id) {
        log.debug("REST request to delete Kunde : {}", id);
        kundeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
