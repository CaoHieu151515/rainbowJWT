package com.auth0.rainbow.web.rest;

import com.auth0.rainbow.repository.AppPostRepository;
import com.auth0.rainbow.service.AppPostService;
import com.auth0.rainbow.service.dto.AppPostDTO;
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
 * REST controller for managing {@link com.auth0.rainbow.domain.AppPost}.
 */
@RestController
@RequestMapping("/api")
public class AppPostResource {

    private final Logger log = LoggerFactory.getLogger(AppPostResource.class);

    private static final String ENTITY_NAME = "appPost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppPostService appPostService;

    private final AppPostRepository appPostRepository;

    public AppPostResource(AppPostService appPostService, AppPostRepository appPostRepository) {
        this.appPostService = appPostService;
        this.appPostRepository = appPostRepository;
    }

    /**
     * {@code POST  /app-posts} : Create a new appPost.
     *
     * @param appPostDTO the appPostDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appPostDTO, or with status {@code 400 (Bad Request)} if the appPost has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-posts")
    public ResponseEntity<AppPostDTO> createAppPost(@RequestBody AppPostDTO appPostDTO) throws URISyntaxException {
        log.debug("REST request to save AppPost : {}", appPostDTO);
        if (appPostDTO.getId() != null) {
            throw new BadRequestAlertException("A new appPost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppPostDTO result = appPostService.save(appPostDTO);
        return ResponseEntity
            .created(new URI("/api/app-posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-posts/:id} : Updates an existing appPost.
     *
     * @param id the id of the appPostDTO to save.
     * @param appPostDTO the appPostDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appPostDTO,
     * or with status {@code 400 (Bad Request)} if the appPostDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appPostDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-posts/{id}")
    public ResponseEntity<AppPostDTO> updateAppPost(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppPostDTO appPostDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppPost : {}, {}", id, appPostDTO);
        if (appPostDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appPostDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appPostRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppPostDTO result = appPostService.update(appPostDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appPostDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-posts/:id} : Partial updates given fields of an existing appPost, field will ignore if it is null
     *
     * @param id the id of the appPostDTO to save.
     * @param appPostDTO the appPostDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appPostDTO,
     * or with status {@code 400 (Bad Request)} if the appPostDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appPostDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appPostDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-posts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppPostDTO> partialUpdateAppPost(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppPostDTO appPostDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppPost partially : {}, {}", id, appPostDTO);
        if (appPostDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appPostDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appPostRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppPostDTO> result = appPostService.partialUpdate(appPostDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appPostDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-posts} : get all the appPosts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appPosts in body.
     */
    @GetMapping("/app-posts")
    public List<AppPostDTO> getAllAppPosts() {
        log.debug("REST request to get all AppPosts");
        return appPostService.findAll();
    }

    /**
     * {@code GET  /app-posts/:id} : get the "id" appPost.
     *
     * @param id the id of the appPostDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appPostDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-posts/{id}")
    public ResponseEntity<AppPostDTO> getAppPost(@PathVariable Long id) {
        log.debug("REST request to get AppPost : {}", id);
        Optional<AppPostDTO> appPostDTO = appPostService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appPostDTO);
    }

    /**
     * {@code DELETE  /app-posts/:id} : delete the "id" appPost.
     *
     * @param id the id of the appPostDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-posts/{id}")
    public ResponseEntity<Void> deleteAppPost(@PathVariable Long id) {
        log.debug("REST request to delete AppPost : {}", id);
        appPostService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
