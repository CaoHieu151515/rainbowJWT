package com.auth0.rainbow.web.rest;

import com.auth0.rainbow.repository.AppLessonPDFRepository;
import com.auth0.rainbow.service.AppLessonPDFService;
import com.auth0.rainbow.service.dto.AppLessonPDFDTO;
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
 * REST controller for managing {@link com.auth0.rainbow.domain.AppLessonPDF}.
 */
@RestController
@RequestMapping("/api")
public class AppLessonPDFResource {

    private final Logger log = LoggerFactory.getLogger(AppLessonPDFResource.class);

    private static final String ENTITY_NAME = "appLessonPDF";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppLessonPDFService appLessonPDFService;

    private final AppLessonPDFRepository appLessonPDFRepository;

    public AppLessonPDFResource(AppLessonPDFService appLessonPDFService, AppLessonPDFRepository appLessonPDFRepository) {
        this.appLessonPDFService = appLessonPDFService;
        this.appLessonPDFRepository = appLessonPDFRepository;
    }

    /**
     * {@code POST  /app-lesson-pdfs} : Create a new appLessonPDF.
     *
     * @param appLessonPDFDTO the appLessonPDFDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appLessonPDFDTO, or with status {@code 400 (Bad Request)} if the appLessonPDF has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-lesson-pdfs")
    public ResponseEntity<AppLessonPDFDTO> createAppLessonPDF(@RequestBody AppLessonPDFDTO appLessonPDFDTO) throws URISyntaxException {
        log.debug("REST request to save AppLessonPDF : {}", appLessonPDFDTO);
        if (appLessonPDFDTO.getId() != null) {
            throw new BadRequestAlertException("A new appLessonPDF cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppLessonPDFDTO result = appLessonPDFService.save(appLessonPDFDTO);
        return ResponseEntity
            .created(new URI("/api/app-lesson-pdfs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-lesson-pdfs/:id} : Updates an existing appLessonPDF.
     *
     * @param id the id of the appLessonPDFDTO to save.
     * @param appLessonPDFDTO the appLessonPDFDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appLessonPDFDTO,
     * or with status {@code 400 (Bad Request)} if the appLessonPDFDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appLessonPDFDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-lesson-pdfs/{id}")
    public ResponseEntity<AppLessonPDFDTO> updateAppLessonPDF(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppLessonPDFDTO appLessonPDFDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppLessonPDF : {}, {}", id, appLessonPDFDTO);
        if (appLessonPDFDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appLessonPDFDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appLessonPDFRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppLessonPDFDTO result = appLessonPDFService.update(appLessonPDFDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appLessonPDFDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-lesson-pdfs/:id} : Partial updates given fields of an existing appLessonPDF, field will ignore if it is null
     *
     * @param id the id of the appLessonPDFDTO to save.
     * @param appLessonPDFDTO the appLessonPDFDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appLessonPDFDTO,
     * or with status {@code 400 (Bad Request)} if the appLessonPDFDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appLessonPDFDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appLessonPDFDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-lesson-pdfs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppLessonPDFDTO> partialUpdateAppLessonPDF(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppLessonPDFDTO appLessonPDFDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppLessonPDF partially : {}, {}", id, appLessonPDFDTO);
        if (appLessonPDFDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appLessonPDFDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appLessonPDFRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppLessonPDFDTO> result = appLessonPDFService.partialUpdate(appLessonPDFDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appLessonPDFDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-lesson-pdfs} : get all the appLessonPDFS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appLessonPDFS in body.
     */
    @GetMapping("/app-lesson-pdfs")
    public List<AppLessonPDFDTO> getAllAppLessonPDFS() {
        log.debug("REST request to get all AppLessonPDFS");
        return appLessonPDFService.findAll();
    }

    /**
     * {@code GET  /app-lesson-pdfs/:id} : get the "id" appLessonPDF.
     *
     * @param id the id of the appLessonPDFDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appLessonPDFDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-lesson-pdfs/{id}")
    public ResponseEntity<AppLessonPDFDTO> getAppLessonPDF(@PathVariable Long id) {
        log.debug("REST request to get AppLessonPDF : {}", id);
        Optional<AppLessonPDFDTO> appLessonPDFDTO = appLessonPDFService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appLessonPDFDTO);
    }

    /**
     * {@code DELETE  /app-lesson-pdfs/:id} : delete the "id" appLessonPDF.
     *
     * @param id the id of the appLessonPDFDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-lesson-pdfs/{id}")
    public ResponseEntity<Void> deleteAppLessonPDF(@PathVariable Long id) {
        log.debug("REST request to delete AppLessonPDF : {}", id);
        appLessonPDFService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
