package com.auth0.rainbow.web.rest;

import com.auth0.rainbow.domain.AppOrder;
import com.auth0.rainbow.repository.AppOrderRepository;
import com.auth0.rainbow.service.AppOrderService;
import com.auth0.rainbow.service.dto.AppOrderDTO;
import com.auth0.rainbow.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.auth0.rainbow.domain.AppOrder}.
 */
@RestController
@RequestMapping("/api")
public class AppOrderResource {

    private final Logger log = LoggerFactory.getLogger(AppOrderResource.class);

    private static final String ENTITY_NAME = "appOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppOrderService appOrderService;

    private final AppOrderRepository appOrderRepository;

    public AppOrderResource(AppOrderService appOrderService, AppOrderRepository appOrderRepository) {
        this.appOrderService = appOrderService;
        this.appOrderRepository = appOrderRepository;
    }

    /**
     * {@code POST  /app-orders} : Create a new appOrder.
     *
     * @param appOrderDTO the appOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appOrderDTO, or with status {@code 400 (Bad Request)} if the appOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-orders")
    public ResponseEntity<AppOrderDTO> createAppOrder(@RequestBody AppOrderDTO appOrderDTO) throws URISyntaxException {
        log.debug("REST request to save AppOrder : {}", appOrderDTO);
        if (appOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new appOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppOrderDTO result = appOrderService.save(appOrderDTO);
        return ResponseEntity
            .created(new URI("/api/app-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-orders/:id} : Updates an existing appOrder.
     *
     * @param id the id of the appOrderDTO to save.
     * @param appOrderDTO the appOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appOrderDTO,
     * or with status {@code 400 (Bad Request)} if the appOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-orders/{id}")
    public ResponseEntity<AppOrderDTO> updateAppOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppOrderDTO appOrderDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppOrder : {}, {}", id, appOrderDTO);
        if (appOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appOrderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppOrderDTO result = appOrderService.update(appOrderDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-orders/:id} : Partial updates given fields of an existing appOrder, field will ignore if it is null
     *
     * @param id the id of the appOrderDTO to save.
     * @param appOrderDTO the appOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appOrderDTO,
     * or with status {@code 400 (Bad Request)} if the appOrderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appOrderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-orders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppOrderDTO> partialUpdateAppOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppOrderDTO appOrderDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppOrder partially : {}, {}", id, appOrderDTO);
        if (appOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appOrderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppOrderDTO> result = appOrderService.partialUpdate(appOrderDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appOrderDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-orders} : get all the appOrders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appOrders in body.
     */
    @GetMapping("/app-orders")
    public ResponseEntity<List<AppOrderDTO>> getAllAppOrders(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AppOrders");
        Page<AppOrderDTO> page = appOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/generate-unique-random-number")
    public ResponseEntity<Long> generateUniqueRandomNumber() {
        List<AppOrder> appOrders = appOrderRepository.findAll();
        Long randomNumber = RandomNumberGenerator.generateUniqueRandomNumber(appOrders);
        return ResponseEntity.ok(randomNumber);
    }

    /**
     * {@code GET  /app-orders/:id} : get the "id" appOrder.
     *
     * @param id the id of the appOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-orders/{id}")
    public ResponseEntity<AppOrderDTO> getAppOrder(@PathVariable Long id) {
        log.debug("REST request to get AppOrder : {}", id);
        Optional<AppOrderDTO> appOrderDTO = appOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appOrderDTO);
    }

    /**
     * {@code DELETE  /app-orders/:id} : delete the "id" appOrder.
     *
     * @param id the id of the appOrderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-orders/{id}")
    public ResponseEntity<Void> deleteAppOrder(@PathVariable Long id) {
        log.debug("REST request to delete AppOrder : {}", id);
        appOrderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
