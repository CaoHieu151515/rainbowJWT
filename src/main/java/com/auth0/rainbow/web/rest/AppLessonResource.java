package com.auth0.rainbow.web.rest;

import com.auth0.rainbow.repository.AppLessonRepository;
import com.auth0.rainbow.service.AppLessonService;
import com.auth0.rainbow.service.dto.AppLessonDTO;
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
 * REST controller for managing {@link com.auth0.rainbow.domain.AppLesson}.
 */
@RestController
@RequestMapping("/api")
public class AppLessonResource {

    private final Logger log = LoggerFactory.getLogger(AppLessonResource.class);

    private static final String ENTITY_NAME = "appLesson";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppLessonService appLessonService;

    private final AppLessonRepository appLessonRepository;

    public AppLessonResource(AppLessonService appLessonService, AppLessonRepository appLessonRepository) {
        this.appLessonService = appLessonService;
        this.appLessonRepository = appLessonRepository;
    }

    /**
     * {@code POST  /app-lessons} : Create a new appLesson.
     *
     * @param appLessonDTO the appLessonDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appLessonDTO, or with status {@code 400 (Bad Request)} if the appLesson has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-lessons")
    public ResponseEntity<AppLessonDTO> createAppLesson(@RequestBody AppLessonDTO appLessonDTO) throws URISyntaxException {
        log.debug("REST request to save AppLesson : {}", appLessonDTO);
        if (appLessonDTO.getId() != null) {
            throw new BadRequestAlertException("A new appLesson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppLessonDTO result = appLessonService.save(appLessonDTO);
        return ResponseEntity
            .created(new URI("/api/app-lessons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-lessons/:id} : Updates an existing appLesson.
     *
     * @param id the id of the appLessonDTO to save.
     * @param appLessonDTO the appLessonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appLessonDTO,
     * or with status {@code 400 (Bad Request)} if the appLessonDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appLessonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-lessons/{id}")
    public ResponseEntity<AppLessonDTO> updateAppLesson(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppLessonDTO appLessonDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppLesson : {}, {}", id, appLessonDTO);
        if (appLessonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appLessonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appLessonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppLessonDTO result = appLessonService.update(appLessonDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appLessonDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-lessons/:id} : Partial updates given fields of an existing appLesson, field will ignore if it is null
     *
     * @param id the id of the appLessonDTO to save.
     * @param appLessonDTO the appLessonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appLessonDTO,
     * or with status {@code 400 (Bad Request)} if the appLessonDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appLessonDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appLessonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-lessons/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppLessonDTO> partialUpdateAppLesson(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppLessonDTO appLessonDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppLesson partially : {}, {}", id, appLessonDTO);
        if (appLessonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appLessonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appLessonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppLessonDTO> result = appLessonService.partialUpdate(appLessonDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appLessonDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-lessons} : get all the appLessons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appLessons in body.
     */
    @GetMapping("/app-lessons")
    public List<AppLessonDTO> getAllAppLessons() {
        log.debug("REST request to get all AppLessons");
        return appLessonService.findAll();
    }

    /**
     * {@code GET  /app-lessons/:id} : get the "id" appLesson.
     *
     * @param id the id of the appLessonDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appLessonDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-lessons/{id}")
    public ResponseEntity<AppLessonDTO> getAppLesson(@PathVariable Long id) {
        log.debug("REST request to get AppLesson : {}", id);
        Optional<AppLessonDTO> appLessonDTO = appLessonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appLessonDTO);
    }

    /**
     * {@code DELETE  /app-lessons/:id} : delete the "id" appLesson.
     *
     * @param id the id of the appLessonDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-lessons/{id}")
    public ResponseEntity<Void> deleteAppLesson(@PathVariable Long id) {
        log.debug("REST request to delete AppLesson : {}", id);
        appLessonService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
