package com.auth0.rainbow.web.rest;

import com.auth0.rainbow.repository.AppProductRepository;
import com.auth0.rainbow.service.AppProductService;
import com.auth0.rainbow.service.dto.AppProductDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.auth0.rainbow.domain.AppProduct}.
 */
@RestController
@RequestMapping("/api")
public class AppProductResource {

    private final Logger log = LoggerFactory.getLogger(AppProductResource.class);

    private static final String ENTITY_NAME = "appProduct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppProductService appProductService;

    private final AppProductRepository appProductRepository;

    public AppProductResource(AppProductService appProductService, AppProductRepository appProductRepository) {
        this.appProductService = appProductService;
        this.appProductRepository = appProductRepository;
    }

    /**
     * {@code POST  /app-products} : Create a new appProduct.
     *
     * @param appProductDTO the appProductDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appProductDTO, or with status {@code 400 (Bad Request)} if the appProduct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-products")
    public ResponseEntity<AppProductDTO> createAppProduct(@RequestBody AppProductDTO appProductDTO) throws URISyntaxException {
        log.debug("REST request to save AppProduct : {}", appProductDTO);
        if (appProductDTO.getId() != null) {
            throw new BadRequestAlertException("A new appProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppProductDTO result = appProductService.save(appProductDTO);
        return ResponseEntity
            .created(new URI("/api/app-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-products/:id} : Updates an existing appProduct.
     *
     * @param id the id of the appProductDTO to save.
     * @param appProductDTO the appProductDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appProductDTO,
     * or with status {@code 400 (Bad Request)} if the appProductDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appProductDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-products/{id}")
    public ResponseEntity<AppProductDTO> updateAppProduct(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppProductDTO appProductDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppProduct : {}, {}", id, appProductDTO);
        if (appProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appProductDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appProductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppProductDTO result = appProductService.update(appProductDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-products/:id} : Partial updates given fields of an existing appProduct, field will ignore if it is null
     *
     * @param id the id of the appProductDTO to save.
     * @param appProductDTO the appProductDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appProductDTO,
     * or with status {@code 400 (Bad Request)} if the appProductDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appProductDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appProductDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-products/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppProductDTO> partialUpdateAppProduct(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppProductDTO appProductDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppProduct partially : {}, {}", id, appProductDTO);
        if (appProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appProductDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appProductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppProductDTO> result = appProductService.partialUpdate(appProductDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appProductDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-products} : get all the appProducts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appProducts in body.
     */
    @GetMapping("/app-products")
    public ResponseEntity<List<AppProductDTO>> getAllAppProducts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AppProducts");
        Page<AppProductDTO> page = appProductService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /app-products/:id} : get the "id" appProduct.
     *
     * @param id the id of the appProductDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appProductDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-products/{id}")
    public ResponseEntity<AppProductDTO> getAppProduct(@PathVariable Long id) {
        log.debug("REST request to get AppProduct : {}", id);
        Optional<AppProductDTO> appProductDTO = appProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appProductDTO);
    }

    /**
     * {@code DELETE  /app-products/:id} : delete the "id" appProduct.
     *
     * @param id the id of the appProductDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-products/{id}")
    public ResponseEntity<Void> deleteAppProduct(@PathVariable Long id) {
        log.debug("REST request to delete AppProduct : {}", id);
        appProductService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
