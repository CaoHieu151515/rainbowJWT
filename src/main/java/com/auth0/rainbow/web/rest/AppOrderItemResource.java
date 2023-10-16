package com.auth0.rainbow.web.rest;

import com.auth0.rainbow.repository.AppOrderItemRepository;
import com.auth0.rainbow.service.AppOrderItemService;
import com.auth0.rainbow.service.dto.AppOrderItemDTO;
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
 * REST controller for managing {@link com.auth0.rainbow.domain.AppOrderItem}.
 */
@RestController
@RequestMapping("/api")
public class AppOrderItemResource {

    private final Logger log = LoggerFactory.getLogger(AppOrderItemResource.class);

    private static final String ENTITY_NAME = "appOrderItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppOrderItemService appOrderItemService;

    private final AppOrderItemRepository appOrderItemRepository;

    public AppOrderItemResource(AppOrderItemService appOrderItemService, AppOrderItemRepository appOrderItemRepository) {
        this.appOrderItemService = appOrderItemService;
        this.appOrderItemRepository = appOrderItemRepository;
    }

    /**
     * {@code POST  /app-order-items} : Create a new appOrderItem.
     *
     * @param appOrderItemDTO the appOrderItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appOrderItemDTO, or with status {@code 400 (Bad Request)} if the appOrderItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-order-items")
    public ResponseEntity<AppOrderItemDTO> createAppOrderItem(@RequestBody AppOrderItemDTO appOrderItemDTO) throws URISyntaxException {
        log.debug("REST request to save AppOrderItem : {}", appOrderItemDTO);
        if (appOrderItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new appOrderItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppOrderItemDTO result = appOrderItemService.save(appOrderItemDTO);
        return ResponseEntity
            .created(new URI("/api/app-order-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-order-items/:id} : Updates an existing appOrderItem.
     *
     * @param id the id of the appOrderItemDTO to save.
     * @param appOrderItemDTO the appOrderItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appOrderItemDTO,
     * or with status {@code 400 (Bad Request)} if the appOrderItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appOrderItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-order-items/{id}")
    public ResponseEntity<AppOrderItemDTO> updateAppOrderItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppOrderItemDTO appOrderItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppOrderItem : {}, {}", id, appOrderItemDTO);
        if (appOrderItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appOrderItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appOrderItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppOrderItemDTO result = appOrderItemService.update(appOrderItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appOrderItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-order-items/:id} : Partial updates given fields of an existing appOrderItem, field will ignore if it is null
     *
     * @param id the id of the appOrderItemDTO to save.
     * @param appOrderItemDTO the appOrderItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appOrderItemDTO,
     * or with status {@code 400 (Bad Request)} if the appOrderItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appOrderItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appOrderItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-order-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppOrderItemDTO> partialUpdateAppOrderItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppOrderItemDTO appOrderItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppOrderItem partially : {}, {}", id, appOrderItemDTO);
        if (appOrderItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appOrderItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appOrderItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppOrderItemDTO> result = appOrderItemService.partialUpdate(appOrderItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appOrderItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-order-items} : get all the appOrderItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appOrderItems in body.
     */
    @GetMapping("/app-order-items")
    public List<AppOrderItemDTO> getAllAppOrderItems() {
        log.debug("REST request to get all AppOrderItems");
        return appOrderItemService.findAll();
    }

    /**
     * {@code GET  /app-order-items/:id} : get the "id" appOrderItem.
     *
     * @param id the id of the appOrderItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appOrderItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-order-items/{id}")
    public ResponseEntity<AppOrderItemDTO> getAppOrderItem(@PathVariable Long id) {
        log.debug("REST request to get AppOrderItem : {}", id);
        Optional<AppOrderItemDTO> appOrderItemDTO = appOrderItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appOrderItemDTO);
    }

    /**
     * {@code DELETE  /app-order-items/:id} : delete the "id" appOrderItem.
     *
     * @param id the id of the appOrderItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-order-items/{id}")
    public ResponseEntity<Void> deleteAppOrderItem(@PathVariable Long id) {
        log.debug("REST request to delete AppOrderItem : {}", id);
        appOrderItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
