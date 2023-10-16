package com.auth0.rainbow.service;

import com.auth0.rainbow.service.dto.AppCategoryDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.auth0.rainbow.domain.AppCategory}.
 */
public interface AppCategoryService {
    /**
     * Save a appCategory.
     *
     * @param appCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    AppCategoryDTO save(AppCategoryDTO appCategoryDTO);

    /**
     * Updates a appCategory.
     *
     * @param appCategoryDTO the entity to update.
     * @return the persisted entity.
     */
    AppCategoryDTO update(AppCategoryDTO appCategoryDTO);

    /**
     * Partially updates a appCategory.
     *
     * @param appCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppCategoryDTO> partialUpdate(AppCategoryDTO appCategoryDTO);

    /**
     * Get all the appCategories.
     *
     * @return the list of entities.
     */
    List<AppCategoryDTO> findAll();

    /**
     * Get the "id" appCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" appCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
