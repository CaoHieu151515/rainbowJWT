package com.auth0.rainbow.service;

import com.auth0.rainbow.service.dto.AppAvailableCourseDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.auth0.rainbow.domain.AppAvailableCourse}.
 */
public interface AppAvailableCourseService {
    /**
     * Save a appAvailableCourse.
     *
     * @param appAvailableCourseDTO the entity to save.
     * @return the persisted entity.
     */
    AppAvailableCourseDTO save(AppAvailableCourseDTO appAvailableCourseDTO);

    /**
     * Updates a appAvailableCourse.
     *
     * @param appAvailableCourseDTO the entity to update.
     * @return the persisted entity.
     */
    AppAvailableCourseDTO update(AppAvailableCourseDTO appAvailableCourseDTO);

    /**
     * Partially updates a appAvailableCourse.
     *
     * @param appAvailableCourseDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppAvailableCourseDTO> partialUpdate(AppAvailableCourseDTO appAvailableCourseDTO);

    /**
     * Get all the appAvailableCourses.
     *
     * @return the list of entities.
     */
    List<AppAvailableCourseDTO> findAll();

    /**
     * Get the "id" appAvailableCourse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppAvailableCourseDTO> findOne(Long id);

    /**
     * Delete the "id" appAvailableCourse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
