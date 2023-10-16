package com.auth0.rainbow.web.rest;

import com.auth0.rainbow.repository.AppCategoryRepository;
import com.auth0.rainbow.service.AppCategoryService;
import com.auth0.rainbow.service.dto.AppCategoryDTO;
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
 * REST controller for managing {@link com.auth0.rainbow.domain.AppCategory}.
 */
@RestController
@RequestMapping("/api")
public class AppCategoryResource {

    private final Logger log = LoggerFactory.getLogger(AppCategoryResource.class);

    private static final String ENTITY_NAME = "appCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppCategoryService appCategoryService;

    private final AppCategoryRepository appCategoryRepository;

    public AppCategoryResource(AppCategoryService appCategoryService, AppCategoryRepository appCategoryRepository) {
        this.appCategoryService = appCategoryService;
        this.appCategoryRepository = appCategoryRepository;
    }

    /**
     * {@code POST  /app-categories} : Create a new appCategory.
     *
     * @param appCategoryDTO the appCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appCategoryDTO, or with status {@code 400 (Bad Request)} if the appCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-categories")
    public ResponseEntity<AppCategoryDTO> createAppCategory(@RequestBody AppCategoryDTO appCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save AppCategory : {}", appCategoryDTO);
        if (appCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new appCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppCategoryDTO result = appCategoryService.save(appCategoryDTO);
        return ResponseEntity
            .created(new URI("/api/app-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-categories/:id} : Updates an existing appCategory.
     *
     * @param id the id of the appCategoryDTO to save.
     * @param appCategoryDTO the appCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the appCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-categories/{id}")
    public ResponseEntity<AppCategoryDTO> updateAppCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppCategoryDTO appCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppCategory : {}, {}", id, appCategoryDTO);
        if (appCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppCategoryDTO result = appCategoryService.update(appCategoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-categories/:id} : Partial updates given fields of an existing appCategory, field will ignore if it is null
     *
     * @param id the id of the appCategoryDTO to save.
     * @param appCategoryDTO the appCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the appCategoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appCategoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppCategoryDTO> partialUpdateAppCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppCategoryDTO appCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppCategory partially : {}, {}", id, appCategoryDTO);
        if (appCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppCategoryDTO> result = appCategoryService.partialUpdate(appCategoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appCategoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-categories} : get all the appCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appCategories in body.
     */
    @GetMapping("/app-categories")
    public List<AppCategoryDTO> getAllAppCategories() {
        log.debug("REST request to get all AppCategories");
        return appCategoryService.findAll();
    }

    /**
     * {@code GET  /app-categories/:id} : get the "id" appCategory.
     *
     * @param id the id of the appCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-categories/{id}")
    public ResponseEntity<AppCategoryDTO> getAppCategory(@PathVariable Long id) {
        log.debug("REST request to get AppCategory : {}", id);
        Optional<AppCategoryDTO> appCategoryDTO = appCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appCategoryDTO);
    }

    /**
     * {@code DELETE  /app-categories/:id} : delete the "id" appCategory.
     *
     * @param id the id of the appCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-categories/{id}")
    public ResponseEntity<Void> deleteAppCategory(@PathVariable Long id) {
        log.debug("REST request to delete AppCategory : {}", id);
        appCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
