package com.auth0.rainbow.web.rest;

import com.auth0.rainbow.repository.AppAvailableCourseRepository;
import com.auth0.rainbow.service.AppAvailableCourseService;
import com.auth0.rainbow.service.dto.AppAvailableCourseDTO;
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
 * REST controller for managing {@link com.auth0.rainbow.domain.AppAvailableCourse}.
 */
@RestController
@RequestMapping("/api")
public class AppAvailableCourseResource {

    private final Logger log = LoggerFactory.getLogger(AppAvailableCourseResource.class);

    private static final String ENTITY_NAME = "appAvailableCourse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppAvailableCourseService appAvailableCourseService;

    private final AppAvailableCourseRepository appAvailableCourseRepository;

    public AppAvailableCourseResource(
        AppAvailableCourseService appAvailableCourseService,
        AppAvailableCourseRepository appAvailableCourseRepository
    ) {
        this.appAvailableCourseService = appAvailableCourseService;
        this.appAvailableCourseRepository = appAvailableCourseRepository;
    }

    /**
     * {@code POST  /app-available-courses} : Create a new appAvailableCourse.
     *
     * @param appAvailableCourseDTO the appAvailableCourseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appAvailableCourseDTO, or with status {@code 400 (Bad Request)} if the appAvailableCourse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-available-courses")
    public ResponseEntity<AppAvailableCourseDTO> createAppAvailableCourse(@RequestBody AppAvailableCourseDTO appAvailableCourseDTO)
        throws URISyntaxException {
        log.debug("REST request to save AppAvailableCourse : {}", appAvailableCourseDTO);
        if (appAvailableCourseDTO.getId() != null) {
            throw new BadRequestAlertException("A new appAvailableCourse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppAvailableCourseDTO result = appAvailableCourseService.save(appAvailableCourseDTO);
        return ResponseEntity
            .created(new URI("/api/app-available-courses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-available-courses/:id} : Updates an existing appAvailableCourse.
     *
     * @param id the id of the appAvailableCourseDTO to save.
     * @param appAvailableCourseDTO the appAvailableCourseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appAvailableCourseDTO,
     * or with status {@code 400 (Bad Request)} if the appAvailableCourseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appAvailableCourseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-available-courses/{id}")
    public ResponseEntity<AppAvailableCourseDTO> updateAppAvailableCourse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppAvailableCourseDTO appAvailableCourseDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppAvailableCourse : {}, {}", id, appAvailableCourseDTO);
        if (appAvailableCourseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appAvailableCourseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appAvailableCourseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppAvailableCourseDTO result = appAvailableCourseService.update(appAvailableCourseDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appAvailableCourseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-available-courses/:id} : Partial updates given fields of an existing appAvailableCourse, field will ignore if it is null
     *
     * @param id the id of the appAvailableCourseDTO to save.
     * @param appAvailableCourseDTO the appAvailableCourseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appAvailableCourseDTO,
     * or with status {@code 400 (Bad Request)} if the appAvailableCourseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appAvailableCourseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appAvailableCourseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-available-courses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppAvailableCourseDTO> partialUpdateAppAvailableCourse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppAvailableCourseDTO appAvailableCourseDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppAvailableCourse partially : {}, {}", id, appAvailableCourseDTO);
        if (appAvailableCourseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appAvailableCourseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appAvailableCourseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppAvailableCourseDTO> result = appAvailableCourseService.partialUpdate(appAvailableCourseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appAvailableCourseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-available-courses} : get all the appAvailableCourses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appAvailableCourses in body.
     */
    @GetMapping("/app-available-courses")
    public List<AppAvailableCourseDTO> getAllAppAvailableCourses() {
        log.debug("REST request to get all AppAvailableCourses");
        return appAvailableCourseService.findAll();
    }

    /**
     * {@code GET  /app-available-courses/:id} : get the "id" appAvailableCourse.
     *
     * @param id the id of the appAvailableCourseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appAvailableCourseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-available-courses/{id}")
    public ResponseEntity<AppAvailableCourseDTO> getAppAvailableCourse(@PathVariable Long id) {
        log.debug("REST request to get AppAvailableCourse : {}", id);
        Optional<AppAvailableCourseDTO> appAvailableCourseDTO = appAvailableCourseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appAvailableCourseDTO);
    }

    /**
     * {@code DELETE  /app-available-courses/:id} : delete the "id" appAvailableCourse.
     *
     * @param id the id of the appAvailableCourseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-available-courses/{id}")
    public ResponseEntity<Void> deleteAppAvailableCourse(@PathVariable Long id) {
        log.debug("REST request to delete AppAvailableCourse : {}", id);
        appAvailableCourseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
