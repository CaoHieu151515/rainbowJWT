package com.auth0.rainbow.web.rest;

import com.auth0.rainbow.repository.AppPaymentRepository;
import com.auth0.rainbow.service.AppPaymentService;
import com.auth0.rainbow.service.dto.AppPaymentDTO;
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
 * REST controller for managing {@link com.auth0.rainbow.domain.AppPayment}.
 */
@RestController
@RequestMapping("/api")
public class AppPaymentResource {

    private final Logger log = LoggerFactory.getLogger(AppPaymentResource.class);

    private static final String ENTITY_NAME = "appPayment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppPaymentService appPaymentService;

    private final AppPaymentRepository appPaymentRepository;

    public AppPaymentResource(AppPaymentService appPaymentService, AppPaymentRepository appPaymentRepository) {
        this.appPaymentService = appPaymentService;
        this.appPaymentRepository = appPaymentRepository;
    }

    /**
     * {@code POST  /app-payments} : Create a new appPayment.
     *
     * @param appPaymentDTO the appPaymentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appPaymentDTO, or with status {@code 400 (Bad Request)} if the appPayment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-payments")
    public ResponseEntity<AppPaymentDTO> createAppPayment(@RequestBody AppPaymentDTO appPaymentDTO) throws URISyntaxException {
        log.debug("REST request to save AppPayment : {}", appPaymentDTO);
        if (appPaymentDTO.getId() != null) {
            throw new BadRequestAlertException("A new appPayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppPaymentDTO result = appPaymentService.save(appPaymentDTO);
        return ResponseEntity
            .created(new URI("/api/app-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-payments/:id} : Updates an existing appPayment.
     *
     * @param id the id of the appPaymentDTO to save.
     * @param appPaymentDTO the appPaymentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appPaymentDTO,
     * or with status {@code 400 (Bad Request)} if the appPaymentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appPaymentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-payments/{id}")
    public ResponseEntity<AppPaymentDTO> updateAppPayment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppPaymentDTO appPaymentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppPayment : {}, {}", id, appPaymentDTO);
        if (appPaymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appPaymentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appPaymentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppPaymentDTO result = appPaymentService.update(appPaymentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appPaymentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-payments/:id} : Partial updates given fields of an existing appPayment, field will ignore if it is null
     *
     * @param id the id of the appPaymentDTO to save.
     * @param appPaymentDTO the appPaymentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appPaymentDTO,
     * or with status {@code 400 (Bad Request)} if the appPaymentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appPaymentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appPaymentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-payments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppPaymentDTO> partialUpdateAppPayment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppPaymentDTO appPaymentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppPayment partially : {}, {}", id, appPaymentDTO);
        if (appPaymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appPaymentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appPaymentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppPaymentDTO> result = appPaymentService.partialUpdate(appPaymentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appPaymentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-payments} : get all the appPayments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appPayments in body.
     */
    @GetMapping("/app-payments")
    public List<AppPaymentDTO> getAllAppPayments() {
        log.debug("REST request to get all AppPayments");
        return appPaymentService.findAll();
    }

    /**
     * {@code GET  /app-payments/:id} : get the "id" appPayment.
     *
     * @param id the id of the appPaymentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appPaymentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-payments/{id}")
    public ResponseEntity<AppPaymentDTO> getAppPayment(@PathVariable Long id) {
        log.debug("REST request to get AppPayment : {}", id);
        Optional<AppPaymentDTO> appPaymentDTO = appPaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appPaymentDTO);
    }

    /**
     * {@code DELETE  /app-payments/:id} : delete the "id" appPayment.
     *
     * @param id the id of the appPaymentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-payments/{id}")
    public ResponseEntity<Void> deleteAppPayment(@PathVariable Long id) {
        log.debug("REST request to delete AppPayment : {}", id);
        appPaymentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
