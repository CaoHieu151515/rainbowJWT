package com.auth0.rainbow.service;

import com.auth0.rainbow.service.dto.AppPostDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.auth0.rainbow.domain.AppPost}.
 */
public interface AppPostService {
    /**
     * Save a appPost.
     *
     * @param appPostDTO the entity to save.
     * @return the persisted entity.
     */
    AppPostDTO save(AppPostDTO appPostDTO);

    /**
     * Updates a appPost.
     *
     * @param appPostDTO the entity to update.
     * @return the persisted entity.
     */
    AppPostDTO update(AppPostDTO appPostDTO);

    /**
     * Partially updates a appPost.
     *
     * @param appPostDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppPostDTO> partialUpdate(AppPostDTO appPostDTO);

    /**
     * Get all the appPosts.
     *
     * @return the list of entities.
     */
    List<AppPostDTO> findAll();

    /**
     * Get the "id" appPost.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppPostDTO> findOne(Long id);

    /**
     * Delete the "id" appPost.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
