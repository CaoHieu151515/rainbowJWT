package com.auth0.rainbow.web.rest;

import com.auth0.rainbow.repository.AppCourseRepository;
import com.auth0.rainbow.service.AppCourseService;
import com.auth0.rainbow.service.dto.AppCourseDTO;
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
 * REST controller for managing {@link com.auth0.rainbow.domain.AppCourse}.
 */
@RestController
@RequestMapping("/api")
public class AppCourseResource {

    private final Logger log = LoggerFactory.getLogger(AppCourseResource.class);

    private static final String ENTITY_NAME = "appCourse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppCourseService appCourseService;

    private final AppCourseRepository appCourseRepository;

    public AppCourseResource(AppCourseService appCourseService, AppCourseRepository appCourseRepository) {
        this.appCourseService = appCourseService;
        this.appCourseRepository = appCourseRepository;
    }

    /**
     * {@code POST  /app-courses} : Create a new appCourse.
     *
     * @param appCourseDTO the appCourseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appCourseDTO, or with status {@code 400 (Bad Request)} if the appCourse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-courses")
    public ResponseEntity<AppCourseDTO> createAppCourse(@RequestBody AppCourseDTO appCourseDTO) throws URISyntaxException {
        log.debug("REST request to save AppCourse : {}", appCourseDTO);
        if (appCourseDTO.getId() != null) {
            throw new BadRequestAlertException("A new appCourse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppCourseDTO result = appCourseService.save(appCourseDTO);
        return ResponseEntity
            .created(new URI("/api/app-courses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-courses/:id} : Updates an existing appCourse.
     *
     * @param id the id of the appCourseDTO to save.
     * @param appCourseDTO the appCourseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appCourseDTO,
     * or with status {@code 400 (Bad Request)} if the appCourseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appCourseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-courses/{id}")
    public ResponseEntity<AppCourseDTO> updateAppCourse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppCourseDTO appCourseDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppCourse : {}, {}", id, appCourseDTO);
        if (appCourseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appCourseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appCourseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppCourseDTO result = appCourseService.update(appCourseDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appCourseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-courses/:id} : Partial updates given fields of an existing appCourse, field will ignore if it is null
     *
     * @param id the id of the appCourseDTO to save.
     * @param appCourseDTO the appCourseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appCourseDTO,
     * or with status {@code 400 (Bad Request)} if the appCourseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appCourseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appCourseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-courses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppCourseDTO> partialUpdateAppCourse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppCourseDTO appCourseDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppCourse partially : {}, {}", id, appCourseDTO);
        if (appCourseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appCourseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appCourseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppCourseDTO> result = appCourseService.partialUpdate(appCourseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appCourseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-courses} : get all the appCourses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appCourses in body.
     */
    @GetMapping("/app-courses")
    public List<AppCourseDTO> getAllAppCourses() {
        log.debug("REST request to get all AppCourses");
        return appCourseService.findAll();
    }

    /**
     * {@code GET  /app-courses/:id} : get the "id" appCourse.
     *
     * @param id the id of the appCourseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appCourseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-courses/{id}")
    public ResponseEntity<AppCourseDTO> getAppCourse(@PathVariable Long id) {
        log.debug("REST request to get AppCourse : {}", id);
        Optional<AppCourseDTO> appCourseDTO = appCourseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appCourseDTO);
    }

    /**
     * {@code DELETE  /app-courses/:id} : delete the "id" appCourse.
     *
     * @param id the id of the appCourseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-courses/{id}")
    public ResponseEntity<Void> deleteAppCourse(@PathVariable Long id) {
        log.debug("REST request to delete AppCourse : {}", id);
        appCourseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
