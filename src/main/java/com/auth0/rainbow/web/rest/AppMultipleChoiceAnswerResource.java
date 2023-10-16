package com.auth0.rainbow.web.rest;

import com.auth0.rainbow.repository.AppMultipleChoiceAnswerRepository;
import com.auth0.rainbow.service.AppMultipleChoiceAnswerService;
import com.auth0.rainbow.service.dto.AppMultipleChoiceAnswerDTO;
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
 * REST controller for managing {@link com.auth0.rainbow.domain.AppMultipleChoiceAnswer}.
 */
@RestController
@RequestMapping("/api")
public class AppMultipleChoiceAnswerResource {

    private final Logger log = LoggerFactory.getLogger(AppMultipleChoiceAnswerResource.class);

    private static final String ENTITY_NAME = "appMultipleChoiceAnswer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppMultipleChoiceAnswerService appMultipleChoiceAnswerService;

    private final AppMultipleChoiceAnswerRepository appMultipleChoiceAnswerRepository;

    public AppMultipleChoiceAnswerResource(
        AppMultipleChoiceAnswerService appMultipleChoiceAnswerService,
        AppMultipleChoiceAnswerRepository appMultipleChoiceAnswerRepository
    ) {
        this.appMultipleChoiceAnswerService = appMultipleChoiceAnswerService;
        this.appMultipleChoiceAnswerRepository = appMultipleChoiceAnswerRepository;
    }

    /**
     * {@code POST  /app-multiple-choice-answers} : Create a new appMultipleChoiceAnswer.
     *
     * @param appMultipleChoiceAnswerDTO the appMultipleChoiceAnswerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appMultipleChoiceAnswerDTO, or with status {@code 400 (Bad Request)} if the appMultipleChoiceAnswer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-multiple-choice-answers")
    public ResponseEntity<AppMultipleChoiceAnswerDTO> createAppMultipleChoiceAnswer(
        @RequestBody AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO
    ) throws URISyntaxException {
        log.debug("REST request to save AppMultipleChoiceAnswer : {}", appMultipleChoiceAnswerDTO);
        if (appMultipleChoiceAnswerDTO.getId() != null) {
            throw new BadRequestAlertException("A new appMultipleChoiceAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppMultipleChoiceAnswerDTO result = appMultipleChoiceAnswerService.save(appMultipleChoiceAnswerDTO);
        return ResponseEntity
            .created(new URI("/api/app-multiple-choice-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-multiple-choice-answers/:id} : Updates an existing appMultipleChoiceAnswer.
     *
     * @param id the id of the appMultipleChoiceAnswerDTO to save.
     * @param appMultipleChoiceAnswerDTO the appMultipleChoiceAnswerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appMultipleChoiceAnswerDTO,
     * or with status {@code 400 (Bad Request)} if the appMultipleChoiceAnswerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appMultipleChoiceAnswerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-multiple-choice-answers/{id}")
    public ResponseEntity<AppMultipleChoiceAnswerDTO> updateAppMultipleChoiceAnswer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppMultipleChoiceAnswer : {}, {}", id, appMultipleChoiceAnswerDTO);
        if (appMultipleChoiceAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appMultipleChoiceAnswerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appMultipleChoiceAnswerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppMultipleChoiceAnswerDTO result = appMultipleChoiceAnswerService.update(appMultipleChoiceAnswerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appMultipleChoiceAnswerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-multiple-choice-answers/:id} : Partial updates given fields of an existing appMultipleChoiceAnswer, field will ignore if it is null
     *
     * @param id the id of the appMultipleChoiceAnswerDTO to save.
     * @param appMultipleChoiceAnswerDTO the appMultipleChoiceAnswerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appMultipleChoiceAnswerDTO,
     * or with status {@code 400 (Bad Request)} if the appMultipleChoiceAnswerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appMultipleChoiceAnswerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appMultipleChoiceAnswerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-multiple-choice-answers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppMultipleChoiceAnswerDTO> partialUpdateAppMultipleChoiceAnswer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppMultipleChoiceAnswer partially : {}, {}", id, appMultipleChoiceAnswerDTO);
        if (appMultipleChoiceAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appMultipleChoiceAnswerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appMultipleChoiceAnswerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppMultipleChoiceAnswerDTO> result = appMultipleChoiceAnswerService.partialUpdate(appMultipleChoiceAnswerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appMultipleChoiceAnswerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-multiple-choice-answers} : get all the appMultipleChoiceAnswers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appMultipleChoiceAnswers in body.
     */
    @GetMapping("/app-multiple-choice-answers")
    public List<AppMultipleChoiceAnswerDTO> getAllAppMultipleChoiceAnswers() {
        log.debug("REST request to get all AppMultipleChoiceAnswers");
        return appMultipleChoiceAnswerService.findAll();
    }

    /**
     * {@code GET  /app-multiple-choice-answers/:id} : get the "id" appMultipleChoiceAnswer.
     *
     * @param id the id of the appMultipleChoiceAnswerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appMultipleChoiceAnswerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-multiple-choice-answers/{id}")
    public ResponseEntity<AppMultipleChoiceAnswerDTO> getAppMultipleChoiceAnswer(@PathVariable Long id) {
        log.debug("REST request to get AppMultipleChoiceAnswer : {}", id);
        Optional<AppMultipleChoiceAnswerDTO> appMultipleChoiceAnswerDTO = appMultipleChoiceAnswerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appMultipleChoiceAnswerDTO);
    }

    /**
     * {@code DELETE  /app-multiple-choice-answers/:id} : delete the "id" appMultipleChoiceAnswer.
     *
     * @param id the id of the appMultipleChoiceAnswerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-multiple-choice-answers/{id}")
    public ResponseEntity<Void> deleteAppMultipleChoiceAnswer(@PathVariable Long id) {
        log.debug("REST request to delete AppMultipleChoiceAnswer : {}", id);
        appMultipleChoiceAnswerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
