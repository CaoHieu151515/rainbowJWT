package com.auth0.rainbow.service;

import com.auth0.rainbow.service.dto.AppPostImageDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.auth0.rainbow.domain.AppPostImage}.
 */
public interface AppPostImageService {
    /**
     * Save a appPostImage.
     *
     * @param appPostImageDTO the entity to save.
     * @return the persisted entity.
     */
    AppPostImageDTO save(AppPostImageDTO appPostImageDTO);

    /**
     * Updates a appPostImage.
     *
     * @param appPostImageDTO the entity to update.
     * @return the persisted entity.
     */
    AppPostImageDTO update(AppPostImageDTO appPostImageDTO);

    /**
     * Partially updates a appPostImage.
     *
     * @param appPostImageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppPostImageDTO> partialUpdate(AppPostImageDTO appPostImageDTO);

    /**
     * Get all the appPostImages.
     *
     * @return the list of entities.
     */
    List<AppPostImageDTO> findAll();

    /**
     * Get the "id" appPostImage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppPostImageDTO> findOne(Long id);

    /**
     * Delete the "id" appPostImage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
