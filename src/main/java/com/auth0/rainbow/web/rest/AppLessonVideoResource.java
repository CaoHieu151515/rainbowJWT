package com.auth0.rainbow.web.rest;

import com.auth0.rainbow.repository.AppLessonVideoRepository;
import com.auth0.rainbow.service.AppLessonVideoService;
import com.auth0.rainbow.service.dto.AppLessonVideoDTO;
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
 * REST controller for managing {@link com.auth0.rainbow.domain.AppLessonVideo}.
 */
@RestController
@RequestMapping("/api")
public class AppLessonVideoResource {

    private final Logger log = LoggerFactory.getLogger(AppLessonVideoResource.class);

    private static final String ENTITY_NAME = "appLessonVideo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppLessonVideoService appLessonVideoService;

    private final AppLessonVideoRepository appLessonVideoRepository;

    public AppLessonVideoResource(AppLessonVideoService appLessonVideoService, AppLessonVideoRepository appLessonVideoRepository) {
        this.appLessonVideoService = appLessonVideoService;
        this.appLessonVideoRepository = appLessonVideoRepository;
    }

    /**
     * {@code POST  /app-lesson-videos} : Create a new appLessonVideo.
     *
     * @param appLessonVideoDTO the appLessonVideoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appLessonVideoDTO, or with status {@code 400 (Bad Request)} if the appLessonVideo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-lesson-videos")
    public ResponseEntity<AppLessonVideoDTO> createAppLessonVideo(@RequestBody AppLessonVideoDTO appLessonVideoDTO)
        throws URISyntaxException {
        log.debug("REST request to save AppLessonVideo : {}", appLessonVideoDTO);
        if (appLessonVideoDTO.getId() != null) {
            throw new BadRequestAlertException("A new appLessonVideo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppLessonVideoDTO result = appLessonVideoService.save(appLessonVideoDTO);
        return ResponseEntity
            .created(new URI("/api/app-lesson-videos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-lesson-videos/:id} : Updates an existing appLessonVideo.
     *
     * @param id the id of the appLessonVideoDTO to save.
     * @param appLessonVideoDTO the appLessonVideoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appLessonVideoDTO,
     * or with status {@code 400 (Bad Request)} if the appLessonVideoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appLessonVideoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-lesson-videos/{id}")
    public ResponseEntity<AppLessonVideoDTO> updateAppLessonVideo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppLessonVideoDTO appLessonVideoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppLessonVideo : {}, {}", id, appLessonVideoDTO);
        if (appLessonVideoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appLessonVideoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appLessonVideoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppLessonVideoDTO result = appLessonVideoService.update(appLessonVideoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appLessonVideoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-lesson-videos/:id} : Partial updates given fields of an existing appLessonVideo, field will ignore if it is null
     *
     * @param id the id of the appLessonVideoDTO to save.
     * @param appLessonVideoDTO the appLessonVideoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appLessonVideoDTO,
     * or with status {@code 400 (Bad Request)} if the appLessonVideoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appLessonVideoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appLessonVideoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-lesson-videos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppLessonVideoDTO> partialUpdateAppLessonVideo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppLessonVideoDTO appLessonVideoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppLessonVideo partially : {}, {}", id, appLessonVideoDTO);
        if (appLessonVideoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appLessonVideoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appLessonVideoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppLessonVideoDTO> result = appLessonVideoService.partialUpdate(appLessonVideoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appLessonVideoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-lesson-videos} : get all the appLessonVideos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appLessonVideos in body.
     */
    @GetMapping("/app-lesson-videos")
    public List<AppLessonVideoDTO> getAllAppLessonVideos() {
        log.debug("REST request to get all AppLessonVideos");
        return appLessonVideoService.findAll();
    }

    /**
     * {@code GET  /app-lesson-videos/:id} : get the "id" appLessonVideo.
     *
     * @param id the id of the appLessonVideoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appLessonVideoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-lesson-videos/{id}")
    public ResponseEntity<AppLessonVideoDTO> getAppLessonVideo(@PathVariable Long id) {
        log.debug("REST request to get AppLessonVideo : {}", id);
        Optional<AppLessonVideoDTO> appLessonVideoDTO = appLessonVideoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appLessonVideoDTO);
    }

    /**
     * {@code DELETE  /app-lesson-videos/:id} : delete the "id" appLessonVideo.
     *
     * @param id the id of the appLessonVideoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-lesson-videos/{id}")
    public ResponseEntity<Void> deleteAppLessonVideo(@PathVariable Long id) {
        log.debug("REST request to delete AppLessonVideo : {}", id);
        appLessonVideoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
