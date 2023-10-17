package com.auth0.rainbow.web.rest;

import com.auth0.rainbow.repository.LinkAccountUserRepository;
import com.auth0.rainbow.service.LinkAccountUserService;
import com.auth0.rainbow.service.dto.LinkAccountUserDTO;
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
 * REST controller for managing {@link com.auth0.rainbow.domain.LinkAccountUser}.
 */
@RestController
@RequestMapping("/api")
public class LinkAccountUserResource {

    private final Logger log = LoggerFactory.getLogger(LinkAccountUserResource.class);

    private static final String ENTITY_NAME = "linkAccountUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LinkAccountUserService linkAccountUserService;

    private final LinkAccountUserRepository linkAccountUserRepository;

    public LinkAccountUserResource(LinkAccountUserService linkAccountUserService, LinkAccountUserRepository linkAccountUserRepository) {
        this.linkAccountUserService = linkAccountUserService;
        this.linkAccountUserRepository = linkAccountUserRepository;
    }

    /**
     * {@code POST  /link-account-users} : Create a new linkAccountUser.
     *
     * @param linkAccountUserDTO the linkAccountUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new linkAccountUserDTO, or with status {@code 400 (Bad Request)} if the linkAccountUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/link-account-users")
    public ResponseEntity<LinkAccountUserDTO> createLinkAccountUser(@RequestBody LinkAccountUserDTO linkAccountUserDTO)
        throws URISyntaxException {
        log.debug("REST request to save LinkAccountUser : {}", linkAccountUserDTO);
        if (linkAccountUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new linkAccountUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LinkAccountUserDTO result = linkAccountUserService.save(linkAccountUserDTO);
        return ResponseEntity
            .created(new URI("/api/link-account-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /link-account-users/:id} : Updates an existing linkAccountUser.
     *
     * @param id the id of the linkAccountUserDTO to save.
     * @param linkAccountUserDTO the linkAccountUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated linkAccountUserDTO,
     * or with status {@code 400 (Bad Request)} if the linkAccountUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the linkAccountUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/link-account-users/{id}")
    public ResponseEntity<LinkAccountUserDTO> updateLinkAccountUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LinkAccountUserDTO linkAccountUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LinkAccountUser : {}, {}", id, linkAccountUserDTO);
        if (linkAccountUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, linkAccountUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!linkAccountUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LinkAccountUserDTO result = linkAccountUserService.update(linkAccountUserDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, linkAccountUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /link-account-users/:id} : Partial updates given fields of an existing linkAccountUser, field will ignore if it is null
     *
     * @param id the id of the linkAccountUserDTO to save.
     * @param linkAccountUserDTO the linkAccountUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated linkAccountUserDTO,
     * or with status {@code 400 (Bad Request)} if the linkAccountUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the linkAccountUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the linkAccountUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/link-account-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LinkAccountUserDTO> partialUpdateLinkAccountUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LinkAccountUserDTO linkAccountUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LinkAccountUser partially : {}, {}", id, linkAccountUserDTO);
        if (linkAccountUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, linkAccountUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!linkAccountUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LinkAccountUserDTO> result = linkAccountUserService.partialUpdate(linkAccountUserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, linkAccountUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /link-account-users} : get all the linkAccountUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of linkAccountUsers in body.
     */
    @GetMapping("/link-account-users")
    public List<LinkAccountUserDTO> getAllLinkAccountUsers() {
        log.debug("REST request to get all LinkAccountUsers");
        return linkAccountUserService.findAll();
    }

    /**
     * {@code GET  /link-account-users/:id} : get the "id" linkAccountUser.
     *
     * @param id the id of the linkAccountUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the linkAccountUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/link-account-users/{id}")
    public ResponseEntity<LinkAccountUserDTO> getLinkAccountUser(@PathVariable Long id) {
        log.debug("REST request to get LinkAccountUser : {}", id);
        Optional<LinkAccountUserDTO> linkAccountUserDTO = linkAccountUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(linkAccountUserDTO);
    }

    @GetMapping("/link-account-users/Get-Full-Details/{id}")
    public ResponseEntity<LinkAccountUserDTO> getLinkAccountUserFull(@PathVariable Long id) {
        log.debug("REST request to get LinkAccountUser : {}", id);
        Optional<LinkAccountUserDTO> linkAccountUserDTO = linkAccountUserService.findOnedetails(id);
        return ResponseUtil.wrapOrNotFound(linkAccountUserDTO);
    }

    @GetMapping("/link-account-users/userPost/{id}")
    public ResponseEntity<LinkAccountUserDTO> getuserPost(@PathVariable Long id) {
        log.debug("REST request to get LinkAccountUser : {}", id);
        Optional<LinkAccountUserDTO> linkAccountUserDTO = linkAccountUserService.findOneAppUserPost(id);
        return ResponseUtil.wrapOrNotFound(linkAccountUserDTO);
    }

    /**
     * {@code DELETE  /link-account-users/:id} : delete the "id" linkAccountUser.
     *
     * @param id the id of the linkAccountUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/link-account-users/{id}")
    public ResponseEntity<Void> deleteLinkAccountUser(@PathVariable Long id) {
        log.debug("REST request to delete LinkAccountUser : {}", id);
        linkAccountUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
