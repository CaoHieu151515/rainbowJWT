package com.auth0.rainbow.service;

import com.auth0.rainbow.service.dto.AppProductImageDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.auth0.rainbow.domain.AppProductImage}.
 */
public interface AppProductImageService {
    /**
     * Save a appProductImage.
     *
     * @param appProductImageDTO the entity to save.
     * @return the persisted entity.
     */
    AppProductImageDTO save(AppProductImageDTO appProductImageDTO);

    /**
     * Updates a appProductImage.
     *
     * @param appProductImageDTO the entity to update.
     * @return the persisted entity.
     */
    AppProductImageDTO update(AppProductImageDTO appProductImageDTO);

    /**
     * Partially updates a appProductImage.
     *
     * @param appProductImageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppProductImageDTO> partialUpdate(AppProductImageDTO appProductImageDTO);

    /**
     * Get all the appProductImages.
     *
     * @return the list of entities.
     */
    List<AppProductImageDTO> findAll();

    /**
     * Get the "id" appProductImage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppProductImageDTO> findOne(Long id);

    /**
     * Delete the "id" appProductImage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
