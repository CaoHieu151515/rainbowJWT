package com.auth0.rainbow.web.rest;

import com.auth0.rainbow.repository.AppQuestionVideoInfoRepository;
import com.auth0.rainbow.service.AppQuestionVideoInfoService;
import com.auth0.rainbow.service.dto.AppQuestionVideoInfoDTO;
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
 * REST controller for managing {@link com.auth0.rainbow.domain.AppQuestionVideoInfo}.
 */
@RestController
@RequestMapping("/api")
public class AppQuestionVideoInfoResource {

    private final Logger log = LoggerFactory.getLogger(AppQuestionVideoInfoResource.class);

    private static final String ENTITY_NAME = "appQuestionVideoInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppQuestionVideoInfoService appQuestionVideoInfoService;

    private final AppQuestionVideoInfoRepository appQuestionVideoInfoRepository;

    public AppQuestionVideoInfoResource(
        AppQuestionVideoInfoService appQuestionVideoInfoService,
        AppQuestionVideoInfoRepository appQuestionVideoInfoRepository
    ) {
        this.appQuestionVideoInfoService = appQuestionVideoInfoService;
        this.appQuestionVideoInfoRepository = appQuestionVideoInfoRepository;
    }

    /**
     * {@code POST  /app-question-video-infos} : Create a new appQuestionVideoInfo.
     *
     * @param appQuestionVideoInfoDTO the appQuestionVideoInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appQuestionVideoInfoDTO, or with status {@code 400 (Bad Request)} if the appQuestionVideoInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-question-video-infos")
    public ResponseEntity<AppQuestionVideoInfoDTO> createAppQuestionVideoInfo(@RequestBody AppQuestionVideoInfoDTO appQuestionVideoInfoDTO)
        throws URISyntaxException {
        log.debug("REST request to save AppQuestionVideoInfo : {}", appQuestionVideoInfoDTO);
        if (appQuestionVideoInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new appQuestionVideoInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppQuestionVideoInfoDTO result = appQuestionVideoInfoService.save(appQuestionVideoInfoDTO);
        return ResponseEntity
            .created(new URI("/api/app-question-video-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-question-video-infos/:id} : Updates an existing appQuestionVideoInfo.
     *
     * @param id the id of the appQuestionVideoInfoDTO to save.
     * @param appQuestionVideoInfoDTO the appQuestionVideoInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appQuestionVideoInfoDTO,
     * or with status {@code 400 (Bad Request)} if the appQuestionVideoInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appQuestionVideoInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-question-video-infos/{id}")
    public ResponseEntity<AppQuestionVideoInfoDTO> updateAppQuestionVideoInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppQuestionVideoInfoDTO appQuestionVideoInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppQuestionVideoInfo : {}, {}", id, appQuestionVideoInfoDTO);
        if (appQuestionVideoInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appQuestionVideoInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appQuestionVideoInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppQuestionVideoInfoDTO result = appQuestionVideoInfoService.update(appQuestionVideoInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appQuestionVideoInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-question-video-infos/:id} : Partial updates given fields of an existing appQuestionVideoInfo, field will ignore if it is null
     *
     * @param id the id of the appQuestionVideoInfoDTO to save.
     * @param appQuestionVideoInfoDTO the appQuestionVideoInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appQuestionVideoInfoDTO,
     * or with status {@code 400 (Bad Request)} if the appQuestionVideoInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appQuestionVideoInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appQuestionVideoInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-question-video-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppQuestionVideoInfoDTO> partialUpdateAppQuestionVideoInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppQuestionVideoInfoDTO appQuestionVideoInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppQuestionVideoInfo partially : {}, {}", id, appQuestionVideoInfoDTO);
        if (appQuestionVideoInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appQuestionVideoInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appQuestionVideoInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppQuestionVideoInfoDTO> result = appQuestionVideoInfoService.partialUpdate(appQuestionVideoInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appQuestionVideoInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-question-video-infos} : get all the appQuestionVideoInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appQuestionVideoInfos in body.
     */
    @GetMapping("/app-question-video-infos")
    public List<AppQuestionVideoInfoDTO> getAllAppQuestionVideoInfos() {
        log.debug("REST request to get all AppQuestionVideoInfos");
        return appQuestionVideoInfoService.findAll();
    }

    /**
     * {@code GET  /app-question-video-infos/:id} : get the "id" appQuestionVideoInfo.
     *
     * @param id the id of the appQuestionVideoInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appQuestionVideoInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-question-video-infos/{id}")
    public ResponseEntity<AppQuestionVideoInfoDTO> getAppQuestionVideoInfo(@PathVariable Long id) {
        log.debug("REST request to get AppQuestionVideoInfo : {}", id);
        Optional<AppQuestionVideoInfoDTO> appQuestionVideoInfoDTO = appQuestionVideoInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appQuestionVideoInfoDTO);
    }

    /**
     * {@code DELETE  /app-question-video-infos/:id} : delete the "id" appQuestionVideoInfo.
     *
     * @param id the id of the appQuestionVideoInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-question-video-infos/{id}")
    public ResponseEntity<Void> deleteAppQuestionVideoInfo(@PathVariable Long id) {
        log.debug("REST request to delete AppQuestionVideoInfo : {}", id);
        appQuestionVideoInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
