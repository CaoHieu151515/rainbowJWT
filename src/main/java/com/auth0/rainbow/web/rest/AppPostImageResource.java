package com.auth0.rainbow.web.rest;

import com.auth0.rainbow.repository.AppPostImageRepository;
import com.auth0.rainbow.service.AppPostImageService;
import com.auth0.rainbow.service.dto.AppPostImageDTO;
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
 * REST controller for managing {@link com.auth0.rainbow.domain.AppPostImage}.
 */
@RestController
@RequestMapping("/api")
public class AppPostImageResource {

    private final Logger log = LoggerFactory.getLogger(AppPostImageResource.class);

    private static final String ENTITY_NAME = "appPostImage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppPostImageService appPostImageService;

    private final AppPostImageRepository appPostImageRepository;

    public AppPostImageResource(AppPostImageService appPostImageService, AppPostImageRepository appPostImageRepository) {
        this.appPostImageService = appPostImageService;
        this.appPostImageRepository = appPostImageRepository;
    }

    /**
     * {@code POST  /app-post-images} : Create a new appPostImage.
     *
     * @param appPostImageDTO the appPostImageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appPostImageDTO, or with status {@code 400 (Bad Request)} if the appPostImage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-post-images")
    public ResponseEntity<AppPostImageDTO> createAppPostImage(@RequestBody AppPostImageDTO appPostImageDTO) throws URISyntaxException {
        log.debug("REST request to save AppPostImage : {}", appPostImageDTO);
        if (appPostImageDTO.getId() != null) {
            throw new BadRequestAlertException("A new appPostImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppPostImageDTO result = appPostImageService.save(appPostImageDTO);
        return ResponseEntity
            .created(new URI("/api/app-post-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-post-images/:id} : Updates an existing appPostImage.
     *
     * @param id the id of the appPostImageDTO to save.
     * @param appPostImageDTO the appPostImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appPostImageDTO,
     * or with status {@code 400 (Bad Request)} if the appPostImageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appPostImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-post-images/{id}")
    public ResponseEntity<AppPostImageDTO> updateAppPostImage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppPostImageDTO appPostImageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppPostImage : {}, {}", id, appPostImageDTO);
        if (appPostImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appPostImageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appPostImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppPostImageDTO result = appPostImageService.update(appPostImageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appPostImageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-post-images/:id} : Partial updates given fields of an existing appPostImage, field will ignore if it is null
     *
     * @param id the id of the appPostImageDTO to save.
     * @param appPostImageDTO the appPostImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appPostImageDTO,
     * or with status {@code 400 (Bad Request)} if the appPostImageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appPostImageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appPostImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-post-images/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppPostImageDTO> partialUpdateAppPostImage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppPostImageDTO appPostImageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppPostImage partially : {}, {}", id, appPostImageDTO);
        if (appPostImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appPostImageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appPostImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppPostImageDTO> result = appPostImageService.partialUpdate(appPostImageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appPostImageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-post-images} : get all the appPostImages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appPostImages in body.
     */
    @GetMapping("/app-post-images")
    public List<AppPostImageDTO> getAllAppPostImages() {
        log.debug("REST request to get all AppPostImages");
        return appPostImageService.findAll();
    }

    /**
     * {@code GET  /app-post-images/:id} : get the "id" appPostImage.
     *
     * @param id the id of the appPostImageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appPostImageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-post-images/{id}")
    public ResponseEntity<AppPostImageDTO> getAppPostImage(@PathVariable Long id) {
        log.debug("REST request to get AppPostImage : {}", id);
        Optional<AppPostImageDTO> appPostImageDTO = appPostImageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appPostImageDTO);
    }

    /**
     * {@code DELETE  /app-post-images/:id} : delete the "id" appPostImage.
     *
     * @param id the id of the appPostImageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-post-images/{id}")
    public ResponseEntity<Void> deleteAppPostImage(@PathVariable Long id) {
        log.debug("REST request to delete AppPostImage : {}", id);
        appPostImageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
