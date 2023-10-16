package com.auth0.rainbow.web.rest;

import com.auth0.rainbow.repository.AppLessonInfoRepository;
import com.auth0.rainbow.service.AppLessonInfoService;
import com.auth0.rainbow.service.dto.AppLessonInfoDTO;
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
 * REST controller for managing {@link com.auth0.rainbow.domain.AppLessonInfo}.
 */
@RestController
@RequestMapping("/api")
public class AppLessonInfoResource {

    private final Logger log = LoggerFactory.getLogger(AppLessonInfoResource.class);

    private static final String ENTITY_NAME = "appLessonInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppLessonInfoService appLessonInfoService;

    private final AppLessonInfoRepository appLessonInfoRepository;

    public AppLessonInfoResource(AppLessonInfoService appLessonInfoService, AppLessonInfoRepository appLessonInfoRepository) {
        this.appLessonInfoService = appLessonInfoService;
        this.appLessonInfoRepository = appLessonInfoRepository;
    }

    /**
     * {@code POST  /app-lesson-infos} : Create a new appLessonInfo.
     *
     * @param appLessonInfoDTO the appLessonInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appLessonInfoDTO, or with status {@code 400 (Bad Request)} if the appLessonInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-lesson-infos")
    public ResponseEntity<AppLessonInfoDTO> createAppLessonInfo(@RequestBody AppLessonInfoDTO appLessonInfoDTO) throws URISyntaxException {
        log.debug("REST request to save AppLessonInfo : {}", appLessonInfoDTO);
        if (appLessonInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new appLessonInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppLessonInfoDTO result = appLessonInfoService.save(appLessonInfoDTO);
        return ResponseEntity
            .created(new URI("/api/app-lesson-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-lesson-infos/:id} : Updates an existing appLessonInfo.
     *
     * @param id the id of the appLessonInfoDTO to save.
     * @param appLessonInfoDTO the appLessonInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appLessonInfoDTO,
     * or with status {@code 400 (Bad Request)} if the appLessonInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appLessonInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-lesson-infos/{id}")
    public ResponseEntity<AppLessonInfoDTO> updateAppLessonInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppLessonInfoDTO appLessonInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppLessonInfo : {}, {}", id, appLessonInfoDTO);
        if (appLessonInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appLessonInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appLessonInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppLessonInfoDTO result = appLessonInfoService.update(appLessonInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appLessonInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-lesson-infos/:id} : Partial updates given fields of an existing appLessonInfo, field will ignore if it is null
     *
     * @param id the id of the appLessonInfoDTO to save.
     * @param appLessonInfoDTO the appLessonInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appLessonInfoDTO,
     * or with status {@code 400 (Bad Request)} if the appLessonInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appLessonInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appLessonInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-lesson-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppLessonInfoDTO> partialUpdateAppLessonInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppLessonInfoDTO appLessonInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppLessonInfo partially : {}, {}", id, appLessonInfoDTO);
        if (appLessonInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appLessonInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appLessonInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppLessonInfoDTO> result = appLessonInfoService.partialUpdate(appLessonInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appLessonInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-lesson-infos} : get all the appLessonInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appLessonInfos in body.
     */
    @GetMapping("/app-lesson-infos")
    public List<AppLessonInfoDTO> getAllAppLessonInfos() {
        log.debug("REST request to get all AppLessonInfos");
        return appLessonInfoService.findAll();
    }

    /**
     * {@code GET  /app-lesson-infos/:id} : get the "id" appLessonInfo.
     *
     * @param id the id of the appLessonInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appLessonInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-lesson-infos/{id}")
    public ResponseEntity<AppLessonInfoDTO> getAppLessonInfo(@PathVariable Long id) {
        log.debug("REST request to get AppLessonInfo : {}", id);
        Optional<AppLessonInfoDTO> appLessonInfoDTO = appLessonInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appLessonInfoDTO);
    }

    /**
     * {@code DELETE  /app-lesson-infos/:id} : delete the "id" appLessonInfo.
     *
     * @param id the id of the appLessonInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-lesson-infos/{id}")
    public ResponseEntity<Void> deleteAppLessonInfo(@PathVariable Long id) {
        log.debug("REST request to delete AppLessonInfo : {}", id);
        appLessonInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
