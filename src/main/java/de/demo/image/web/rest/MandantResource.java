package de.demo.image.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.demo.image.service.MandantService;
import de.demo.image.web.rest.errors.BadRequestAlertException;
import de.demo.image.web.rest.util.HeaderUtil;
import de.demo.image.service.dto.MandantDTO;
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
 * REST controller for managing Mandant.
 */
@RestController
@RequestMapping("/api")
public class MandantResource {

    private final Logger log = LoggerFactory.getLogger(MandantResource.class);

    private static final String ENTITY_NAME = "mandant";

    private final MandantService mandantService;

    public MandantResource(MandantService mandantService) {
        this.mandantService = mandantService;
    }

    /**
     * POST  /mandants : Create a new mandant.
     *
     * @param mandantDTO the mandantDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mandantDTO, or with status 400 (Bad Request) if the mandant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mandants")
    @Timed
    public ResponseEntity<MandantDTO> createMandant(@RequestBody MandantDTO mandantDTO) throws URISyntaxException {
        log.debug("REST request to save Mandant : {}", mandantDTO);
        if (mandantDTO.getId() != null) {
            throw new BadRequestAlertException("A new mandant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MandantDTO result = mandantService.save(mandantDTO);
        return ResponseEntity.created(new URI("/api/mandants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mandants : Updates an existing mandant.
     *
     * @param mandantDTO the mandantDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mandantDTO,
     * or with status 400 (Bad Request) if the mandantDTO is not valid,
     * or with status 500 (Internal Server Error) if the mandantDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mandants")
    @Timed
    public ResponseEntity<MandantDTO> updateMandant(@RequestBody MandantDTO mandantDTO) throws URISyntaxException {
        log.debug("REST request to update Mandant : {}", mandantDTO);
        if (mandantDTO.getId() == null) {
            return createMandant(mandantDTO);
        }
        MandantDTO result = mandantService.save(mandantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mandantDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mandants : get all the mandants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mandants in body
     */
    @GetMapping("/mandants")
    @Timed
    public List<MandantDTO> getAllMandants() {
        log.debug("REST request to get all Mandants");
        return mandantService.findAll();
        }

    /**
     * GET  /mandants/:id : get the "id" mandant.
     *
     * @param id the id of the mandantDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mandantDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mandants/{id}")
    @Timed
    public ResponseEntity<MandantDTO> getMandant(@PathVariable Long id) {
        log.debug("REST request to get Mandant : {}", id);
        MandantDTO mandantDTO = mandantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mandantDTO));
    }

    /**
     * DELETE  /mandants/:id : delete the "id" mandant.
     *
     * @param id the id of the mandantDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mandants/{id}")
    @Timed
    public ResponseEntity<Void> deleteMandant(@PathVariable Long id) {
        log.debug("REST request to delete Mandant : {}", id);
        mandantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
