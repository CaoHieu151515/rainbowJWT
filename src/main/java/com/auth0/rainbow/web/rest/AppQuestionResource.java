package com.auth0.rainbow.web.rest;

import com.auth0.rainbow.repository.AppQuestionRepository;
import com.auth0.rainbow.service.AppQuestionService;
import com.auth0.rainbow.service.dto.AppQuestionDTO;
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
 * REST controller for managing {@link com.auth0.rainbow.domain.AppQuestion}.
 */
@RestController
@RequestMapping("/api")
public class AppQuestionResource {

    private final Logger log = LoggerFactory.getLogger(AppQuestionResource.class);

    private static final String ENTITY_NAME = "appQuestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppQuestionService appQuestionService;

    private final AppQuestionRepository appQuestionRepository;

    public AppQuestionResource(AppQuestionService appQuestionService, AppQuestionRepository appQuestionRepository) {
        this.appQuestionService = appQuestionService;
        this.appQuestionRepository = appQuestionRepository;
    }

    /**
     * {@code POST  /app-questions} : Create a new appQuestion.
     *
     * @param appQuestionDTO the appQuestionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appQuestionDTO, or with status {@code 400 (Bad Request)} if the appQuestion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-questions")
    public ResponseEntity<AppQuestionDTO> createAppQuestion(@RequestBody AppQuestionDTO appQuestionDTO) throws URISyntaxException {
        log.debug("REST request to save AppQuestion : {}", appQuestionDTO);
        if (appQuestionDTO.getId() != null) {
            throw new BadRequestAlertException("A new appQuestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppQuestionDTO result = appQuestionService.save(appQuestionDTO);
        return ResponseEntity
            .created(new URI("/api/app-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-questions/:id} : Updates an existing appQuestion.
     *
     * @param id the id of the appQuestionDTO to save.
     * @param appQuestionDTO the appQuestionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appQuestionDTO,
     * or with status {@code 400 (Bad Request)} if the appQuestionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appQuestionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-questions/{id}")
    public ResponseEntity<AppQuestionDTO> updateAppQuestion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppQuestionDTO appQuestionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppQuestion : {}, {}", id, appQuestionDTO);
        if (appQuestionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appQuestionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appQuestionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppQuestionDTO result = appQuestionService.update(appQuestionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appQuestionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-questions/:id} : Partial updates given fields of an existing appQuestion, field will ignore if it is null
     *
     * @param id the id of the appQuestionDTO to save.
     * @param appQuestionDTO the appQuestionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appQuestionDTO,
     * or with status {@code 400 (Bad Request)} if the appQuestionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appQuestionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appQuestionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-questions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppQuestionDTO> partialUpdateAppQuestion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppQuestionDTO appQuestionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppQuestion partially : {}, {}", id, appQuestionDTO);
        if (appQuestionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appQuestionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appQuestionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppQuestionDTO> result = appQuestionService.partialUpdate(appQuestionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appQuestionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-questions} : get all the appQuestions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appQuestions in body.
     */
    @GetMapping("/app-questions")
    public List<AppQuestionDTO> getAllAppQuestions() {
        log.debug("REST request to get all AppQuestions");
        return appQuestionService.findAll();
    }

    /**
     * {@code GET  /app-questions/:id} : get the "id" appQuestion.
     *
     * @param id the id of the appQuestionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appQuestionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-questions/{id}")
    public ResponseEntity<AppQuestionDTO> getAppQuestion(@PathVariable Long id) {
        log.debug("REST request to get AppQuestion : {}", id);
        Optional<AppQuestionDTO> appQuestionDTO = appQuestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appQuestionDTO);
    }

    /**
     * {@code DELETE  /app-questions/:id} : delete the "id" appQuestion.
     *
     * @param id the id of the appQuestionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-questions/{id}")
    public ResponseEntity<Void> deleteAppQuestion(@PathVariable Long id) {
        log.debug("REST request to delete AppQuestion : {}", id);
        appQuestionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
