package com.auth0.rainbow.web.rest;

import com.auth0.rainbow.repository.AppCartRepository;
import com.auth0.rainbow.service.AppCartService;
import com.auth0.rainbow.service.dto.AppCartDTO;
import com.auth0.rainbow.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.auth0.rainbow.domain.AppCart}.
 */
@RestController
@RequestMapping("/api")
public class AppCartResource {

    private final Logger log = LoggerFactory.getLogger(AppCartResource.class);

    private static final String ENTITY_NAME = "appCart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppCartService appCartService;

    private final AppCartRepository appCartRepository;

    public AppCartResource(AppCartService appCartService, AppCartRepository appCartRepository) {
        this.appCartService = appCartService;
        this.appCartRepository = appCartRepository;
    }

    /**
     * {@code POST  /app-carts} : Create a new appCart.
     *
     * @param appCartDTO the appCartDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appCartDTO, or with status {@code 400 (Bad Request)} if the appCart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-carts")
    public ResponseEntity<AppCartDTO> createAppCart(@RequestBody AppCartDTO appCartDTO) throws URISyntaxException {
        log.debug("REST request to save AppCart : {}", appCartDTO);
        if (appCartDTO.getId() != null) {
            throw new BadRequestAlertException("A new appCart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppCartDTO result = appCartService.save(appCartDTO);
        return ResponseEntity
            .created(new URI("/api/app-carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-carts/:id} : Updates an existing appCart.
     *
     * @param id the id of the appCartDTO to save.
     * @param appCartDTO the appCartDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appCartDTO,
     * or with status {@code 400 (Bad Request)} if the appCartDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appCartDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-carts/{id}")
    public ResponseEntity<AppCartDTO> updateAppCart(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppCartDTO appCartDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppCart : {}, {}", id, appCartDTO);
        if (appCartDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appCartDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appCartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppCartDTO result = appCartService.update(appCartDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appCartDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-carts/:id} : Partial updates given fields of an existing appCart, field will ignore if it is null
     *
     * @param id the id of the appCartDTO to save.
     * @param appCartDTO the appCartDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appCartDTO,
     * or with status {@code 400 (Bad Request)} if the appCartDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appCartDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appCartDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-carts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppCartDTO> partialUpdateAppCart(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppCartDTO appCartDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppCart partially : {}, {}", id, appCartDTO);
        if (appCartDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appCartDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appCartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppCartDTO> result = appCartService.partialUpdate(appCartDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appCartDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-carts} : get all the appCarts.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appCarts in body.
     */
    @GetMapping("/app-carts")
    public List<AppCartDTO> getAllAppCarts(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all AppCarts");
        return appCartService.findAll();
    }

    /**
     * {@code GET  /app-carts/:id} : get the "id" appCart.
     *
     * @param id the id of the appCartDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appCartDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-carts/{id}")
    public ResponseEntity<AppCartDTO> getAppCart(@PathVariable Long id) {
        log.debug("REST request to get AppCart : {}", id);
        Optional<AppCartDTO> appCartDTO = appCartService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appCartDTO);
    }

    /**
     * {@code DELETE  /app-carts/:id} : delete the "id" appCart.
     *
     * @param id the id of the appCartDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-carts/{id}")
    public ResponseEntity<Void> deleteAppCart(@PathVariable Long id) {
        log.debug("REST request to delete AppCart : {}", id);
        appCartService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
