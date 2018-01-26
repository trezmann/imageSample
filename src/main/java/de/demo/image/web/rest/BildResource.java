package de.demo.image.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.demo.image.service.BildService;
import de.demo.image.web.rest.errors.BadRequestAlertException;
import de.demo.image.web.rest.util.HeaderUtil;
import de.demo.image.service.dto.BildDTO;
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
 * REST controller for managing Bild.
 */
@RestController
@RequestMapping("/api")
public class BildResource {

    private final Logger log = LoggerFactory.getLogger(BildResource.class);

    private static final String ENTITY_NAME = "bild";

    private final BildService bildService;

    public BildResource(BildService bildService) {
        this.bildService = bildService;
    }

    /**
     * POST  /bilds : Create a new bild.
     *
     * @param bildDTO the bildDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bildDTO, or with status 400 (Bad Request) if the bild has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bilds")
    @Timed
    public ResponseEntity<BildDTO> createBild(@RequestBody BildDTO bildDTO) throws URISyntaxException {
        log.debug("REST request to save Bild : {}", bildDTO);
        if (bildDTO.getId() != null) {
            throw new BadRequestAlertException("A new bild cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BildDTO result = bildService.save(bildDTO);
        return ResponseEntity.created(new URI("/api/bilds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bilds : Updates an existing bild.
     *
     * @param bildDTO the bildDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bildDTO,
     * or with status 400 (Bad Request) if the bildDTO is not valid,
     * or with status 500 (Internal Server Error) if the bildDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bilds")
    @Timed
    public ResponseEntity<BildDTO> updateBild(@RequestBody BildDTO bildDTO) throws URISyntaxException {
        log.debug("REST request to update Bild : {}", bildDTO);
        if (bildDTO.getId() == null) {
            return createBild(bildDTO);
        }
        BildDTO result = bildService.save(bildDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bildDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bilds : get all the bilds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bilds in body
     */
    @GetMapping("/bilds")
    @Timed
    public List<BildDTO> getAllBilds() {
        log.debug("REST request to get all Bilds");
        return bildService.findAll();
        }

    /**
     * GET  /bilds/:id : get the "id" bild.
     *
     * @param id the id of the bildDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bildDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bilds/{id}")
    @Timed
    public ResponseEntity<BildDTO> getBild(@PathVariable Long id) {
        log.debug("REST request to get Bild : {}", id);
        BildDTO bildDTO = bildService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bildDTO));
    }

    /**
     * DELETE  /bilds/:id : delete the "id" bild.
     *
     * @param id the id of the bildDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bilds/{id}")
    @Timed
    public ResponseEntity<Void> deleteBild(@PathVariable Long id) {
        log.debug("REST request to delete Bild : {}", id);
        bildService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
