package com.auth0.rainbow.web.rest;

import com.auth0.rainbow.repository.AppProductImageRepository;
import com.auth0.rainbow.service.AppProductImageService;
import com.auth0.rainbow.service.dto.AppProductImageDTO;
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
 * REST controller for managing {@link com.auth0.rainbow.domain.AppProductImage}.
 */
@RestController
@RequestMapping("/api")
public class AppProductImageResource {

    private final Logger log = LoggerFactory.getLogger(AppProductImageResource.class);

    private static final String ENTITY_NAME = "appProductImage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppProductImageService appProductImageService;

    private final AppProductImageRepository appProductImageRepository;

    public AppProductImageResource(AppProductImageService appProductImageService, AppProductImageRepository appProductImageRepository) {
        this.appProductImageService = appProductImageService;
        this.appProductImageRepository = appProductImageRepository;
    }

    /**
     * {@code POST  /app-product-images} : Create a new appProductImage.
     *
     * @param appProductImageDTO the appProductImageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appProductImageDTO, or with status {@code 400 (Bad Request)} if the appProductImage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-product-images")
    public ResponseEntity<AppProductImageDTO> createAppProductImage(@RequestBody AppProductImageDTO appProductImageDTO)
        throws URISyntaxException {
        log.debug("REST request to save AppProductImage : {}", appProductImageDTO);
        if (appProductImageDTO.getId() != null) {
            throw new BadRequestAlertException("A new appProductImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppProductImageDTO result = appProductImageService.save(appProductImageDTO);
        return ResponseEntity
            .created(new URI("/api/app-product-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-product-images/:id} : Updates an existing appProductImage.
     *
     * @param id the id of the appProductImageDTO to save.
     * @param appProductImageDTO the appProductImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appProductImageDTO,
     * or with status {@code 400 (Bad Request)} if the appProductImageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appProductImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-product-images/{id}")
    public ResponseEntity<AppProductImageDTO> updateAppProductImage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppProductImageDTO appProductImageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppProductImage : {}, {}", id, appProductImageDTO);
        if (appProductImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appProductImageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appProductImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppProductImageDTO result = appProductImageService.update(appProductImageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appProductImageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-product-images/:id} : Partial updates given fields of an existing appProductImage, field will ignore if it is null
     *
     * @param id the id of the appProductImageDTO to save.
     * @param appProductImageDTO the appProductImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appProductImageDTO,
     * or with status {@code 400 (Bad Request)} if the appProductImageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appProductImageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appProductImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-product-images/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppProductImageDTO> partialUpdateAppProductImage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppProductImageDTO appProductImageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppProductImage partially : {}, {}", id, appProductImageDTO);
        if (appProductImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appProductImageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appProductImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppProductImageDTO> result = appProductImageService.partialUpdate(appProductImageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appProductImageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-product-images} : get all the appProductImages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appProductImages in body.
     */
    @GetMapping("/app-product-images")
    public List<AppProductImageDTO> getAllAppProductImages() {
        log.debug("REST request to get all AppProductImages");
        return appProductImageService.findAll();
    }

    /**
     * {@code GET  /app-product-images/:id} : get the "id" appProductImage.
     *
     * @param id the id of the appProductImageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appProductImageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-product-images/{id}")
    public ResponseEntity<AppProductImageDTO> getAppProductImage(@PathVariable Long id) {
        log.debug("REST request to get AppProductImage : {}", id);
        Optional<AppProductImageDTO> appProductImageDTO = appProductImageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appProductImageDTO);
    }

    /**
     * {@code DELETE  /app-product-images/:id} : delete the "id" appProductImage.
     *
     * @param id the id of the appProductImageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-product-images/{id}")
    public ResponseEntity<Void> deleteAppProductImage(@PathVariable Long id) {
        log.debug("REST request to delete AppProductImage : {}", id);
        appProductImageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
