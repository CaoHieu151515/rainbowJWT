package com.auth0.rainbow.service;

import com.auth0.rainbow.service.dto.AppCartDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.auth0.rainbow.domain.AppCart}.
 */
public interface AppCartService {
    /**
     * Save a appCart.
     *
     * @param appCartDTO the entity to save.
     * @return the persisted entity.
     */
    AppCartDTO save(AppCartDTO appCartDTO);

    /**
     * Updates a appCart.
     *
     * @param appCartDTO the entity to update.
     * @return the persisted entity.
     */
    AppCartDTO update(AppCartDTO appCartDTO);

    /**
     * Partially updates a appCart.
     *
     * @param appCartDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppCartDTO> partialUpdate(AppCartDTO appCartDTO);

    /**
     * Get all the appCarts.
     *
     * @return the list of entities.
     */
    List<AppCartDTO> findAll();

    /**
     * Get all the appCarts with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AppCartDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" appCart.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppCartDTO> findOne(Long id);

    /**
     * Delete the "id" appCart.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
